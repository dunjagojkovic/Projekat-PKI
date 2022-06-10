package com.security.pki.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;

import com.security.pki.dto.CreateRootDTO;

@Entity
public class Certificate {
	@Id
	private int SerialNumber;
	
	@Column
	private CertificateType type;
	
	@Column
	private Boolean revoked;
	
	@Column(unique = true)
	private String commonName;
	
	@Column
	private String organisationUnit;
	
	@Column
	private String organisationName;
	
	@Column
	private String email;
	
	@Column(unique = true)
	private String alias;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Certificate issuer;
	
	@Column
	private String privateKeyPass;
	@Column
	private String keystoreName;
	@Column
	private String keystorePass;

	@Column
	private Date validFrom;
	
	@Column
	private Date validUntil;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private User user;

	public Certificate(int serialNumber, CertificateType type, Boolean revoked, String commonName,
			String organisationUnit, String organisationName, String email, String alias, Certificate issuer,
			String privateKeyPass, String keystoreName, String keystorePass, Date validFrom, Date validUntil,
			User user) {
		super();
		SerialNumber = serialNumber;
		this.type = type;
		this.revoked = revoked;
		this.commonName = commonName;
		this.organisationUnit = organisationUnit;
		this.organisationName = organisationName;
		this.email = email;
		this.alias = alias;
		this.issuer = issuer;
		this.privateKeyPass = privateKeyPass;
		this.keystoreName = keystoreName;
		this.keystorePass = keystorePass;
		this.validFrom = validFrom;
		this.validUntil = validUntil;
		this.user = user;
	}

	public int getSerialNumber() {
		return SerialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		SerialNumber = serialNumber;
	}

	public Certificate getIssuer() {
		return issuer;
	}

	public void setIssuer(Certificate issuer) {
		this.issuer = issuer;
	}

	public String getPrivateKeyPass() {
		return privateKeyPass;
	}

	public void setPrivateKeyPass(String privateKeyPass) {
		this.privateKeyPass = privateKeyPass;
	}

	public CertificateType getType() {
		return type;
	}

	public void setType(CertificateType type) {
		this.type = type;
	}

	public Boolean getRevoked() {
		return revoked;
	}

	public void setRevoked(Boolean revoked) {
		this.revoked = revoked;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getOrganisationUnit() {
		return organisationUnit;
	}

	public void setOrganisationUnit(String organisationUnit) {
		this.organisationUnit = organisationUnit;
	}

	public String getOrganisationName() {
		return organisationName;
	}

	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	
	
	public String getKeystoreName() {
		return keystoreName;
	}

	public void setKeystoreName(String keystoreName) {
		this.keystoreName = keystoreName;
	}

	public String getKeystorePass() {
		return keystorePass;
	}

	public void setKeystorePass(String keystorePass) {
		this.keystorePass = keystorePass;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser (User user) {
		this.user = user;
	}

	public Certificate(int serialNumber, CertificateType type, Boolean revoked, String commonName,
			String organisationUnit, String organisationName, String email, String alias, Certificate issuer,
			String privateKeyPass, Date validFrom, Date validUntil, User user) {
		super();
		SerialNumber = serialNumber;
		this.type = type;
		this.revoked = revoked;
		this.commonName = commonName;
		this.organisationUnit = organisationUnit;
		this.organisationName = organisationName;
		this.email = email;
		this.alias = alias;
		this.issuer = issuer;
		this.privateKeyPass = privateKeyPass;
		this.validFrom = validFrom;
		this.validUntil = validUntil;
		this.user = user;
	}

	public Certificate(int serialNumber, CertificateType type, Boolean revoked, String commonName,
			String organisationUnit, String organisationName, String email, String alias, Certificate issuer,
			String privateKeyPass) {
		super();
		SerialNumber = serialNumber;
		this.type = type;
		this.revoked = revoked;
		this.commonName = commonName;
		this.organisationUnit = organisationUnit;
		this.organisationName = organisationName;
		this.email = email;
		this.alias = alias;
		this.issuer = issuer;
		this.privateKeyPass = privateKeyPass;
	}

	public Certificate() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public boolean isInIssuerHierarchy(int issuerSerial) {
		if(this.SerialNumber == issuerSerial)
			return true;
		if(this.issuer == null)
			return false;
		if(this.issuer.getSerialNumber() == issuerSerial) {
			return true;
		}
		return issuer.isInIssuerHierarchy(issuerSerial);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + SerialNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Certificate other = (Certificate) obj;
		if (SerialNumber != other.SerialNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Certificate [SerialNumber=" + SerialNumber + ", type=" + type + ", revoked=" + revoked + ", commonName="
				+ commonName + ", organisationUnit=" + organisationUnit + ", organisationName=" + organisationName
				+ ", email=" + email + ", alias=" + alias + ", issuer=" + issuer + ", privateKeyPass=" + privateKeyPass
				+ ", keystoreName=" + keystoreName + ", keystorePass=" + keystorePass + ", validFrom=" + validFrom
				+ ", validUntil=" + validUntil + ", user=" + user + "]";
	}
	
	
}
