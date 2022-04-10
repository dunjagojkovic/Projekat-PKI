package service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.CertificateCreationDTO;
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
import repository.CertificateRepository;

@Service
public class CertificateService {
	
	private String keystorePass;
	@Autowired
	private CertificateRepository certificateRepository;
	
	public List<Certificate> getValidIssuers() {
		return certificateRepository.getValidIssuers();
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
		
		X509Certificate cert = generator.generateCertificate(createRootSubject(rootDTO, keyPair.getPublic(), Integer.toString(serial)), createRootIssuer(rootDTO, keyPair.getPrivate()),CertificateType.CA);
		
		writer.write(rootDTO.getAlias(), keyPair.getPrivate(), rootDTO.getPrivateKeyPass().toCharArray(), cert);
		writer.saveKeyStore("keystore.jks", keystorePass.toCharArray());
		Certificate databaseCertificate = new Certificate(serial, CertificateType.CA, false, rootDTO.getCommonName(), rootDTO.getOrganisationUnit(), rootDTO.getOrganisationName(), rootDTO.getEmail(), rootDTO.getAlias(), null, rootDTO.getPrivateKeyPass(),rootDTO.getBegin(),rootDTO.getEnd());
		saveToDatabase(databaseCertificate);
	}
	
	
	public void addSubToKeyStore(CreateSubDTO subDTO, int serial) {
		KeyStoreWriter writer = new KeyStoreWriter();
		KeyStoreReader reader = new KeyStoreReader();
		CertificateGenerator generator = new CertificateGenerator();
		keystorePass = "123";
		
		KeyPair keyPair = generateKeyPair();

		writer.loadKeyStore("keystore.jks", keystorePass.toCharArray());
		
		X509Certificate cert = generator.generateCertificate(createSubSubject(subDTO, keyPair.getPublic(), Integer.toString(serial)), reader.readIssuerFromStore("keystore.jks", subDTO.getIssuerAlias(), keystorePass.toCharArray(), certificateRepository.findByAlias(subDTO.getIssuerAlias()).getPrivateKeyPass().toCharArray()),subDTO.getUsage());
		
		writer.write(subDTO.getAlias(), keyPair.getPrivate(), subDTO.getPrivateKeyPass().toCharArray(), cert);
		writer.saveKeyStore("keystore.jks", keystorePass.toCharArray());

		Certificate databaseCertificate = new Certificate(serial, subDTO.getUsage(), false, subDTO.getCommonName(), subDTO.getOrganisationUnit(), subDTO.getOrganisationName(), subDTO.getEmail(), subDTO.getAlias(), certificateRepository.findByAlias(subDTO.getIssuerAlias()), subDTO.getPrivateKeyPass(),subDTO.getBegin(),subDTO.getEnd());
		saveToDatabase(databaseCertificate);
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
}
