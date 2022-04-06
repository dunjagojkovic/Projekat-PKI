package dto;

import java.util.Date;

public class CreateRootDTO {
	
	private String commonName;
	private String organisationUnit;
	private String organisationName;
	private String email;
	private String privateKeyPass;
	private String alias;
	private Date begin;
	private Date end;
	
	public CreateRootDTO(String commonName, String organisationUnit, String organisationName, String email,
			String privateKeyPass, String alias, Date begin, Date end) {
		super();
		this.commonName = commonName;
		this.organisationUnit = organisationUnit;
		this.organisationName = organisationName;
		this.email = email;
		this.privateKeyPass = privateKeyPass;
		this.alias = alias;
		this.begin = begin;
		this.end = end;
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
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
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
	public CreateRootDTO(String commonName, String organisationUnit, String organisationName, String email,
			String privateKeyPass, String alias) {
		super();
		this.commonName = commonName;
		this.organisationUnit = organisationUnit;
		this.organisationName = organisationName;
		this.email = email;
		this.privateKeyPass = privateKeyPass;
		this.alias = alias;
	}
	public CreateRootDTO() {
		super();

	}
	@Override
	public String toString() {
		return "CreateRootDTO [commonName=" + commonName + ", organisationUnit=" + organisationUnit
				+ ", organisationName=" + organisationName + ", email=" + email + ", privateKeyPass=" + privateKeyPass
				+ ", alias=" + alias + ", begin=" + begin + ", end=" + end + "]";
	}
	
	
}
