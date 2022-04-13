package dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import model.CertificateType;

@Getter
@Setter
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
	private Long userId;
	private String username;
	private String password;
	private String keystoreName;
	private String keystorePass;


	public CreateSubDTO(String commonName, String organisationUnit, String organisationName, String email,
			String privateKeyPass, String alias, String issuerAlias, Date begin, Date end, CertificateType usage,
			Long userId, String username, String password, String keystoreName, String keystorePass) {
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
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.keystoreName = keystoreName;
		this.keystorePass = keystorePass;
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

	public Long getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "CreateSubDTO [commonName=" + commonName + ", organisationUnit=" + organisationUnit
				+ ", organisationName=" + organisationName + ", email=" + email + ", privateKeyPass=" + privateKeyPass
				+ ", alias=" + alias + ", issuerAlias=" + issuerAlias + ", begin=" + begin + ", end=" + end + ", usage="
				+ usage + ", userId=" + userId + ", username=" + username + ", password=" + password + ", keystoreName="
				+ keystoreName + ", keystorePass=" + keystorePass + "]";
	}
}
