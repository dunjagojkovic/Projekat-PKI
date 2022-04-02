package dto;

import java.util.Date;

public class CertificateCreationDTO {
	private String commonName;
	private String organisationUnit;
	private String organisationName;
	private String country;
	private String email;
	
	private String serialNumber;
	private Date endDate;
	private Date startDate;
	
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
	
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public CertificateCreationDTO(String commonName, String organisationUnit, String organisationName, String country,
			String email, String serialNumber, Date endDate, Date startDate) {
		super();
		this.commonName = commonName;
		this.organisationUnit = organisationUnit;
		this.organisationName = organisationName;
		this.country = country;
		this.email = email;
		this.serialNumber = serialNumber;
		this.endDate = endDate;
		this.startDate = startDate;
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
	
	
}
