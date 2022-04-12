package dto;

import model.Certificate;

public class ValidIssuerDTO {
	private int serialNumber;
	private String alias;
	private String commonName;
	
	public ValidIssuerDTO() { }
	public ValidIssuerDTO(Certificate certificate) {
		this.serialNumber = certificate.getSerialNumber();
		this.alias = certificate.getAlias();
		this.setCommonName(certificate.getCommonName());
	}
	
	public int getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
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
}
