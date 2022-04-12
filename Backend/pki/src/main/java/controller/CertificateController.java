package controller;

import java.util.List;

import dto.*;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import model.Certificate;
import model.User;
import service.CertificateService;
import service.UserService;

@RestController
@RequestMapping(value = "api/certificates")
@CrossOrigin(origins = "http://localhost:4200")
public class CertificateController {

	@Autowired
	private CertificateService certService;
	@Autowired
	private UserService userService;

	@Autowired
	private UserController userController;

	@GetMapping(value = "/getAllCertificates")
	public ResponseEntity<List<CertificateDTO>> getAllCertificates() {
		return new ResponseEntity<>(certService.convertCertificatesToDTO(certService.getAllCertificates()), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getSubordinateCertificates/{username}")
	public ResponseEntity<List<CertificateDTO>> getSubordinateCertificates(@PathVariable String username) {
		return new ResponseEntity<>(certService.convertCertificatesToDTO(certService.getSubordinateCertificates(userService.getUserByUsername(username))), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getValidIssuers")
	public ResponseEntity<List<ValidIssuerDTO>> getValidIssuers() {
		return new ResponseEntity<>(certService.convertValidIssuersToDTO(certService.getValidIssuers()), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getValidIssuersForUser/{username}")
	public ResponseEntity<List<ValidIssuerDTO>> getValidIssuersForUser(@PathVariable String username) {
		return new ResponseEntity<>(certService.convertValidIssuersToDTO(certService.getValidIssuers(userService.getUserByUsername(username))), HttpStatus.OK);
	}
	
	@PostMapping(value = "/revokeCert/{certificateToRevokeSerialNumber}")
	public ResponseEntity<String> revokeCert(@PathVariable int certificateToRevokeSerialNumber) {
		return certService.revokeCert(certificateToRevokeSerialNumber);
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

		System.out.println(rootDTO);
		if(certService.validateRoot(rootDTO)) {
			if(!rootDTO.getUsername().isBlank()  && !rootDTO.getPassword().isBlank()){
				userService.registerUser(new RegistrationDTO(rootDTO.getUsername(), rootDTO.getPassword()));

				rootDTO.setUserId(userService.findByUsername(rootDTO.getUsername()).get().getId());
			}


			certService.addRootToKeyStore(rootDTO,  certService.generateSerial());
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
			if(!subDTO.getUsername().isBlank() && !subDTO.getPassword().isBlank()){
				userService.registerUser(new RegistrationDTO(subDTO.getUsername(), subDTO.getPassword()));
				subDTO.setUserId(userService.findByUsername(subDTO.getUsername()).get().getId());
			}
			certService.addSubToKeyStore(subDTO, certService.generateSerial());
			return new ResponseEntity<>(subDTO, HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(subDTO, HttpStatus.BAD_REQUEST);
		}


	}

}
