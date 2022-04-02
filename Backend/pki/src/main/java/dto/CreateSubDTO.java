package dto;

public class CreateSubDTO {
	
	private String commonName;
	private String organisationUnit;
	private String organisationName;
	private String email;
	private String privateKeyPass;
	private String alias;
	private String issuerAlias;
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
	public CreateSubDTO(String commonName, String organisationUnit, String organisationName, String email,
			String privateKeyPass, String alias, String issuerAlias) {
		super();
		this.commonName = commonName;
		this.organisationUnit = organisationUnit;
		this.organisationName = organisationName;
		this.email = email;
		this.privateKeyPass = privateKeyPass;
		this.alias = alias;
		this.issuerAlias = issuerAlias;
	}
	
	@Override
	public String toString() {
		return "CreateSubDTO [commonName=" + commonName + ", organisationUnit=" + organisationUnit
				+ ", organisationName=" + organisationName + ", email=" + email + ", privateKeyPass=" + privateKeyPass
				+ ", alias=" + alias + ", issuerAlias=" + issuerAlias + "]";
	}
	
	
	
}
