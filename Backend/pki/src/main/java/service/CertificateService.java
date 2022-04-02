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
import java.util.Date;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.stereotype.Service;

import dto.CertificateCreationDTO;
import dto.CreateRootDTO;
import dto.CreateSubDTO;
import helper.CertificateGenerator;
import helper.IssuerData;
import helper.KeyStoreReader;
import helper.KeyStoreWriter;
import helper.SubjectData;

@Service
public class CertificateService {
	
	private String keystorePass;
	
	public boolean validateCert(CertificateCreationDTO certDTO) {
		return !certDTO.getCommonName().isBlank() && !certDTO.getCountry().isBlank() && !certDTO.getEmail().isBlank() && !certDTO.getOrganisationName().isBlank();
	}

	public void addRootToKeyStore(CreateRootDTO rootDTO, String serial) {
		KeyStoreWriter writer = new KeyStoreWriter();
		CertificateGenerator generator = new CertificateGenerator();
		keystorePass = "123";
		
		KeyPair keyPair = generateKeyPair();

		writer.loadKeyStore("keystore.jks", keystorePass.toCharArray());
		
		X509Certificate cert = generator.generateCertificate(createRootSubject(rootDTO, keyPair.getPublic(), serial), createRootIssuer(rootDTO, keyPair.getPrivate()));
		
		writer.write(rootDTO.getAlias(), keyPair.getPrivate(), rootDTO.getPrivateKeyPass().toCharArray(), cert);
		writer.saveKeyStore("keystore.jks", keystorePass.toCharArray());
	}
	
	
	public void addSubToKeyStore(CreateSubDTO subDTO, String serial) {
		KeyStoreWriter writer = new KeyStoreWriter();
		KeyStoreReader reader = new KeyStoreReader();
		CertificateGenerator generator = new CertificateGenerator();
		keystorePass = "123";
		
		KeyPair keyPair = generateKeyPair();

		writer.loadKeyStore("keystore.jks", keystorePass.toCharArray());
		
		X509Certificate cert = generator.generateCertificate(createSubSubject(subDTO, keyPair.getPublic(), serial), reader.readIssuerFromStore("keystore.jks", subDTO.getIssuerAlias(), keystorePass.toCharArray(), keystorePass.toCharArray()));
		
		writer.write(subDTO.getAlias(), keyPair.getPrivate(), subDTO.getPrivateKeyPass().toCharArray(), cert);
		writer.saveKeyStore("keystore.jks", keystorePass.toCharArray());
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
		    
		    return new SubjectData(publicKey, builder.build(), serial, startDate, endDate);
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
		    
		    return new SubjectData(publicKey, builder.build(), serial, startDate, endDate);
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
}
