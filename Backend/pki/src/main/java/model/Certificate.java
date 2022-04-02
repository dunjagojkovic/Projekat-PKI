package model;

import javax.persistence.Column;
import javax.persistence.Id;

public class Certificate {
	@Id
	private String SerialNumber;
	
	@Column
	private CertificateType type;
	
	@Column
	private Boolean revoked;
	
}
