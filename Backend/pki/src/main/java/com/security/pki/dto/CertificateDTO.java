package com.security.pki.dto;

import java.util.Date;

import com.security.pki.model.Certificate;

public class CertificateDTO {
	private int SerialNumber;
	private Boolean revoked;
	private String commonName;
	private String organisationUnit;
	private String organisationName;
	private String email;
	private String alias;
	private String issuer;
	private Date validFrom;
	private Date validUntil;
	
	public CertificateDTO() { }
	
	public CertificateDTO(Certificate certificate) {
		this.SerialNumber = certificate.getSerialNumber();
		this.revoked = certificate.getRevoked();
		this.commonName = certificate.getCommonName();
		this.organisationUnit = certificate.getOrganisationUnit();
		this.organisationName = certificate.getOrganisationName();
		this.email = certificate.getEmail();
		this.alias = certificate.getAlias();
		if(certificate.getIssuer() == null)
			this.issuer = "None";
		else
			this.issuer = certificate.getIssuer().getAlias();
		this.validFrom = certificate.getValidFrom();
		this.validUntil = certificate.getValidUntil();
	}
	
	public int getSerialNumber() {
		return SerialNumber;
	}
	public Boolean getRevoked() {
		return revoked;
	}
	public String getCommonName() {
		return commonName;
	}
	public String getOrganisationUnit() {
		return organisationUnit;
	}
	public String getOrganisationName() {
		return organisationName;
	}
	public String getEmail() {
		return email;
	}
	public String getAlias() {
		return alias;
	}
	public String getIssuer() {
		return issuer;
	}
	public Date getValidFrom() {
		return validFrom;
	}
	public Date getValidUntil() {
		return validUntil;
	}
	public void setSerialNumber(int serialNumber) {
		SerialNumber = serialNumber;
	}
	public void setRevoked(Boolean revoked) {
		this.revoked = revoked;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public void setOrganisationUnit(String organisationUnit) {
		this.organisationUnit = organisationUnit;
	}
	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}
	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
	}
	
	
}
