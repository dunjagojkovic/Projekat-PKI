package controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.CertificateCreationDTO;
import dto.CreateRootDTO;
import dto.CreateSubDTO;
import service.CertificateService;

@RestController
@RequestMapping(value = "api/certificates")
public class CertificateController {
	
	@Autowired
	private CertificateService certService;
	
	
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
	
	@PostMapping(consumes = "application/json", value = "/registerRoot")
	public ResponseEntity<CreateRootDTO> registerRoot(@RequestBody CreateRootDTO rootDTO) {
		Random rand = new Random();
		int serial = rand.nextInt(1000000000);
		
		certService.addRootToKeyStore(rootDTO, Integer.toString(serial));
		return new ResponseEntity<>(rootDTO, HttpStatus.CREATED);


	}
	
	@PostMapping(consumes = "application/json", value = "/registerSub")
	public ResponseEntity<CreateSubDTO> registerSub(@RequestBody CreateSubDTO subDTO) {
		Random rand = new Random();
		int serial = rand.nextInt(1000000000);
		
		certService.addSubToKeyStore(subDTO, Integer.toString(serial));
		return new ResponseEntity<>(subDTO, HttpStatus.CREATED);


	}
	
}
