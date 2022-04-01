package dto;


public class CertificateCreationDTO {
	private String commonName;
	private String organisationUnit;
	private String organisationName;
	private String country;
	private String email;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public CertificateCreationDTO(String commonName, String organisationUnit, String organisationName, String country,
			String email) {
		super();
		this.commonName = commonName;
		this.organisationUnit = organisationUnit;
		this.organisationName = organisationName;
		this.country = country;
		this.email = email;
	}
	
	public CertificateCreationDTO() {
		super();
	}
	@Override
	public String toString() {
		return "CertificateCreationDTO [commonName=" + commonName + ", organisationUnit=" + organisationUnit
				+ ", organisationName=" + organisationName + ", country=" + country + ", email=" + email + "]";
	}
	
	
}
