package dto;

import java.util.Date;

import model.CertificateType;

public class CreateSubDTO {
	
	private String commonName;
	private String organisationUnit;
	private String organisationName;
	private String email;
	private String privateKeyPass;
	private String alias;
	private String issuerAlias;
	private Date begin;
	private Date end;
	private CertificateType usage;
	
	public CreateSubDTO(String commonName, String organisationUnit, String organisationName, String email,
			String privateKeyPass, String alias, String issuerAlias, Date begin, Date end, CertificateType usage) {
		super();
		this.commonName = commonName;
		this.organisationUnit = organisationUnit;
		this.organisationName = organisationName;
		this.email = email;
		this.privateKeyPass = privateKeyPass;
		this.alias = alias;
		this.issuerAlias = issuerAlias;
		this.begin = begin;
		this.end = end;
		this.usage = usage;
	}

	public CertificateType getUsage() {
		return usage;
	}

	public void setUsage(CertificateType usage) {
		this.usage = usage;
	}

	public CreateSubDTO() {
		super();
	}
	public Date getBegin() {
		return begin;
	}
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
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
	public String getPrivateKeyPass() {
		return privateKeyPass;
	}
	public void setPrivateKeyPass(String privateKeyPass) {
		this.privateKeyPass = privateKeyPass;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getIssuerAlias() {
		return issuerAlias;
	}
	public void setIssuerAlias(String issuerAlias) {
		this.issuerAlias = issuerAlias;
	}

	@Override
	public String toString() {
		return "CreateSubDTO [commonName=" + commonName + ", organisationUnit=" + organisationUnit
				+ ", organisationName=" + organisationName + ", email=" + email + ", privateKeyPass=" + privateKeyPass
				+ ", alias=" + alias + ", issuerAlias=" + issuerAlias + ", begin=" + begin + ", end=" + end + ", usage="
				+ usage + "]";
	}
	
	
	
}
