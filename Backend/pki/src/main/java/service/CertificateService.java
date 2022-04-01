package service;

import org.springframework.stereotype.Service;

import dto.CertificateCreationDTO;

@Service
public class CertificateService {
	
	public boolean validateCert(CertificateCreationDTO certDTO) {
		return !certDTO.getCommonName().isBlank() && !certDTO.getCountry().isBlank() && !certDTO.getEmail().isBlank() && !certDTO.getOrganisationName().isBlank();
	}


}
