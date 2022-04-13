package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dto.CertificateCreationDTO;
import dto.CertificateDTO;
import dto.CreateRootDTO;
import dto.CreateSubDTO;
import dto.ValidIssuerDTO;
import helper.CertificateGenerator;
import helper.IssuerData;
import helper.KeyStoreReader;
import helper.KeyStoreWriter;
import helper.SubjectData;
import model.Certificate;
import model.CertificateType;
import model.User;
import repository.CertificateRepository;
import repository.UserRepository;

@Service
public class CertificateService {
	
	private String keystorePass;
	@Autowired
	private CertificateRepository certificateRepository;

	@Autowired
	private UserRepository userRepository;

	public List<Certificate> getAllCertificates() {
		return certificateRepository.findAll();
	}
	
	public List<Certificate> getSubordinateCertificates(User user) {
		List<Certificate> allCertificates = getAllCertificates();
		List<Certificate> directlyHeldCertificates = certificateRepository.findByUser(user);
		for(Certificate c1 : allCertificates) 
		{
			for(Certificate c2: directlyHeldCertificates)
			{
				if(c1.isInIssuerHierarchy(c2.getSerialNumber()))
				{
					directlyHeldCertificates.add(c1);
					break;
				}
			}
		}
		directlyHeldCertificates = directlyHeldCertificates.stream()
			     .distinct()
			     .collect(Collectors.toList());
		return directlyHeldCertificates;
	}
	
	public List<Certificate> getValidIssuers() {
		return certificateRepository.getValidIssuers();
	}
	
	public List<Certificate> getValidIssuers(User user) {
		return certificateRepository.getValidIssuers(user.getId());
	}
	
	public List<CertificateDTO> convertCertificatesToDTO(List<Certificate> certificateList) {
		List<CertificateDTO> DTOList = new ArrayList<CertificateDTO>();
		for(Certificate c : certificateList) {
			DTOList.add(new CertificateDTO(c));
		}
		return DTOList;
	}
	
	public List<ValidIssuerDTO> convertValidIssuersToDTO(List<Certificate> certificateList) {
		List<ValidIssuerDTO> DTOList = new ArrayList<ValidIssuerDTO>();
		for(Certificate c : certificateList) {
			DTOList.add(new ValidIssuerDTO(c));
		}
		return DTOList;
	}
	
	public boolean validateCert(CertificateCreationDTO certDTO) {
		return !certDTO.getCommonName().isBlank() && !certDTO.getCountry().isBlank() && !certDTO.getEmail().isBlank() && !certDTO.getOrganisationName().isBlank();
	}
	
	public boolean validateRoot(CreateRootDTO rootDTO) {
		if(rootDTO.getAlias().isBlank() || rootDTO.getCommonName().isBlank() || rootDTO.getOrganisationName().isBlank() || rootDTO.getOrganisationUnit().isBlank() || rootDTO.getEmail().isBlank() || rootDTO.getPrivateKeyPass().isBlank())
			return false;
		if(certificateRepository.existsByAlias(rootDTO.getAlias()))
			return false;
		if(rootDTO.getBegin().after(rootDTO.getEnd()))
			return false;

		
		return true;
	}
	
	public boolean validateSub(CreateSubDTO subDTO) {
		if(subDTO.getIssuerAlias().isBlank() || subDTO.getAlias().isBlank() || subDTO.getCommonName().isBlank() || subDTO.getOrganisationName().isBlank() || subDTO.getOrganisationUnit().isBlank() || subDTO.getEmail().isBlank() || subDTO.getPrivateKeyPass().isBlank())
			return false;
		if(certificateRepository.existsByAlias(subDTO.getAlias()))
			return false;
		if(!certificateRepository.existsByAlias(subDTO.getIssuerAlias()))
			return false;
		if(subDTO.getBegin().after(subDTO.getEnd()))
			return false;
		Certificate issuer = certificateRepository.findByAlias(subDTO.getIssuerAlias());
		
		if(!issuer.getType().equals(CertificateType.CA))
			return false;
		if(issuer.getValidFrom().after(subDTO.getBegin()))
			return false;
		if(subDTO.getEnd().after(issuer.getValidUntil()))
			return false;
		
		return true;
	}
	
	
	public void saveToDatabase(Certificate certificate) {
		certificateRepository.save(certificate);
		
	}

	public void addRootToKeyStore(CreateRootDTO rootDTO, int serial) {
		KeyStoreWriter writer = new KeyStoreWriter();
		CertificateGenerator generator = new CertificateGenerator();
		keystorePass = "123";
		
		KeyPair keyPair = generateKeyPair();

		writer.loadKeyStore("keystore.jks", keystorePass.toCharArray());
		
		X509Certificate cert = generator.generateCertificate(createRootSubject(rootDTO, keyPair.getPublic(), Integer.toString(serial)), createRootIssuer(rootDTO, keyPair.getPrivate()),CertificateType.CA,true,"");
		
		writer.write(rootDTO.getAlias(), keyPair.getPrivate(), rootDTO.getPrivateKeyPass().toCharArray(), cert);
		writer.saveKeyStore("keystore.jks", keystorePass.toCharArray());
		Certificate databaseCertificate = new Certificate(serial, CertificateType.CA, false, rootDTO.getCommonName(), rootDTO.getOrganisationUnit(), rootDTO.getOrganisationName(), rootDTO.getEmail(), rootDTO.getAlias(), null, rootDTO.getPrivateKeyPass(),rootDTO.getBegin(),rootDTO.getEnd(), userRepository.findByUsername(rootDTO.getUsername()).get());
		saveToDatabase(databaseCertificate);
		saveToFile(rootDTO.getAlias());
	}
	
	
	public void addSubToKeyStore(CreateSubDTO subDTO, int serial) {
		KeyStoreWriter writer = new KeyStoreWriter();
		KeyStoreReader reader = new KeyStoreReader();
		CertificateGenerator generator = new CertificateGenerator();
		keystorePass = "123";
		
		KeyPair keyPair = generateKeyPair();

		writer.loadKeyStore("keystore.jks", keystorePass.toCharArray());
		
		X509Certificate cert = generator.generateCertificate(createSubSubject(subDTO, keyPair.getPublic(), Integer.toString(serial)), reader.readIssuerFromStore("keystore.jks", subDTO.getIssuerAlias(), keystorePass.toCharArray(), certificateRepository.findByAlias(subDTO.getIssuerAlias()).getPrivateKeyPass().toCharArray()),subDTO.getUsage(),false,subDTO.getIssuerAlias());
		
		writer.write(subDTO.getAlias(), keyPair.getPrivate(), subDTO.getPrivateKeyPass().toCharArray(), cert);
		writer.saveKeyStore("keystore.jks", keystorePass.toCharArray());

		Certificate databaseCertificate = new Certificate(serial, subDTO.getUsage(), false, subDTO.getCommonName(), subDTO.getOrganisationUnit(), subDTO.getOrganisationName(), subDTO.getEmail(), subDTO.getAlias(), certificateRepository.findByAlias(subDTO.getIssuerAlias()), subDTO.getPrivateKeyPass(),subDTO.getBegin(),subDTO.getEnd(), userRepository.findByUsername(subDTO.getUsername()).get());
		saveToDatabase(databaseCertificate);
		saveToFile(subDTO.getAlias());
	}
	
	public ResponseEntity<String> revokeCert(int certificateToRevokeSerialNumber) {
		Certificate certificateToRevoke = certificateRepository.findById(certificateToRevokeSerialNumber).orElse(null);
		if(certificateToRevoke == null)
			return new ResponseEntity<>("Certificate not found!", HttpStatus.BAD_REQUEST);
		List<Certificate> allCertificates = getAllCertificates();
		certificateToRevoke.setRevoked(true);
		saveToDatabase(certificateToRevoke);
		for(Certificate c : allCertificates) {
			if(c.isInIssuerHierarchy(certificateToRevokeSerialNumber))
			{
				c.setRevoked(true);
				saveToDatabase(c);
			}
		}
		return new ResponseEntity<>("Successfully revoked certificate!", HttpStatus.OK);
	}
	
	private IssuerData createRootIssuer(CreateRootDTO rootDTO, PrivateKey privateKey) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	    builder.addRDN(BCStyle.CN, rootDTO.getCommonName());
	    builder.addRDN(BCStyle.O, rootDTO.getOrganisationName());
	    builder.addRDN(BCStyle.OU, rootDTO.getOrganisationUnit());
	    builder.addRDN(BCStyle.E, rootDTO.getEmail());
	    
		IssuerData issData = new IssuerData(privateKey,null);
		issData.setX500name(builder.build());
		
		return issData;
	}
	
	private SubjectData createSubSubject(CreateSubDTO subDTO, PublicKey publicKey, String serial) {
		try {
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse("2017-12-31");
			Date endDate = iso8601Formater.parse("2022-12-31");

			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		    builder.addRDN(BCStyle.CN, subDTO.getCommonName());
		    builder.addRDN(BCStyle.O, subDTO.getOrganisationName());
		    builder.addRDN(BCStyle.OU, subDTO.getOrganisationUnit());
		    builder.addRDN(BCStyle.E, subDTO.getEmail());
		    
		    return new SubjectData(publicKey, builder.build(), serial, subDTO.getBegin(), subDTO.getEnd());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private SubjectData createRootSubject(CreateRootDTO rootDTO, PublicKey publicKey, String serial) {
		try {
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse("2017-12-31");
			Date endDate = iso8601Formater.parse("2022-12-31");

			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		    builder.addRDN(BCStyle.CN, rootDTO.getCommonName());
		    builder.addRDN(BCStyle.O, rootDTO.getOrganisationName());
		    builder.addRDN(BCStyle.OU, rootDTO.getOrganisationUnit());
		    builder.addRDN(BCStyle.E, rootDTO.getEmail());
		    
		    return new SubjectData(publicKey, builder.build(), serial, rootDTO.getBegin(), rootDTO.getEnd());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); 
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(2048, random);
			return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
        return null;
	}
	
	public int generateSerial() {

		Random rand = new Random();
		int serial = rand.nextInt(2000000000);
		
		while(certificateRepository.existsById(serial)) {
			serial = rand.nextInt(2000000000);
		}
		
		return serial;
	}

	public List<Certificate> getAllCA(){
		return certificateRepository.findAllByRevokedAndType(false,CertificateType.CA);
	}
	
	public void saveToFile(String alias) {
		KeyStoreReader reader = new KeyStoreReader();
		java.security.cert.Certificate x = reader.readCertificate("keystore.jks", "123", alias);
        try {
            final FileOutputStream os = new FileOutputStream("certificates"+File.separator+alias+".cer");
            os.write("-----BEGIN CERTIFICATE-----\n".getBytes("US-ASCII"));
            os.write(Base64.encodeBase64(x.getEncoded(), true));
            os.write("-----END CERTIFICATE-----\n".getBytes("US-ASCII"));
            os.close();
            
        } catch (CertificateEncodingException | IOException e) {
            e.printStackTrace();
        }
	}
}
