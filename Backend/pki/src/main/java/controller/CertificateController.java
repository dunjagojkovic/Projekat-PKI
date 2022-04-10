package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import dto.CertificateCreationDTO;
import dto.CreateRootDTO;
import dto.CreateSubDTO;
import dto.ValidIssuerDTO;
import model.Certificate;
import service.CertificateService;

@RestController
@RequestMapping(value = "api/certificates")
@CrossOrigin(origins = "http://localhost:4200")
public class CertificateController {

	@Autowired
	private CertificateService certService;


	@GetMapping(value = "/getValidIssuers")
	public ResponseEntity<List<ValidIssuerDTO>> getValidIssuers() {
		return new ResponseEntity<>(certService.convertValidIssuersToDTO(certService.getValidIssuers()), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json", value = "/registerCert")
	public ResponseEntity<CertificateCreationDTO> registerCert(@RequestBody CertificateCreationDTO certDTO) {

		System.out.println(certDTO.toString());

		if(certService.validateCert(certDTO)){
			return new ResponseEntity<>(certDTO, HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(certDTO, HttpStatus.BAD_REQUEST);
		}

	}

	//@PreAuthorize("hasAuthority('Admin')")
	@PostMapping(consumes = "application/json", value = "/registerRoot")
	public ResponseEntity<CreateRootDTO> registerRoot(@RequestBody CreateRootDTO rootDTO) {
		if(certService.validateRoot(rootDTO)) {
			certService.addRootToKeyStore(rootDTO, certService.generateSerial());
			return new ResponseEntity<>(rootDTO, HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(rootDTO, HttpStatus.BAD_REQUEST);
		}


	}

	@PostMapping(consumes = "application/json", value = "/registerSub")
	public ResponseEntity<CreateSubDTO> registerSub(@RequestBody CreateSubDTO subDTO) {
		System.out.println(subDTO.toString());

		if(certService.validateSub(subDTO)) {
			certService.addSubToKeyStore(subDTO, certService.generateSerial());
			return new ResponseEntity<>(subDTO, HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(subDTO, HttpStatus.BAD_REQUEST);
		}

	}

}
