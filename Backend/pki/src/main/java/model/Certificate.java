package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;

import dto.CreateRootDTO;

@Entity
public class Certificate {
	@Id
	private int SerialNumber;
	
	@Column
	private CertificateType type;
	
	@Column
	private Boolean revoked;
	
	@Column
	private String commonName;
	
	@Column
	private String organisationUnit;
	
	@Column
	private String organisationName;
	
	@Column
	private String email;
	
	@Column(unique = true)
	private String alias;
	
	@ManyToOne
	private Certificate issuer;
	
	@Column
	private String privateKeyPass;



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
	
	
	
}
