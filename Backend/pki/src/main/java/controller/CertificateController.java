package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.cert.Certificate;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@RequestMapping(value = "/{file_name}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> getFile(@PathVariable("file_name") String fileName) throws FileNotFoundException {
		
		String path = "certificates"+File.separator+fileName;
		File file = new File(path);
		
		// retrieve contents of "C:/tmp/report.pdf" that were written in showHelp
	    byte[] contents;
		try {
			
			contents = Files.readAllBytes(file.toPath());
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/x-x509-ca-cert");
		    // Here you have to set the actual filename of your pdf
		    headers.setContentDispositionFormData(fileName, fileName);
		    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		    ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
		    return response;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}


	    
		//return new FileSystemResource(file);
		
	}
	
	/*@RequestMapping(value = "/{file_name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public FileSystemResource getFile(@PathVariable("file_name") String fileName) throws FileNotFoundException {
		String path = "certificates"+File.separator+fileName;
		File file = new File(path);
		return new FileSystemResource(file);
		
	}*/
	
	/*@RequestMapping(value = "/{file_name}", method = RequestMethod.GET)
	public void getFile(
	    @PathVariable("file_name") String fileName, 
	    HttpServletResponse response) {
	    try {
	      // get your file as InputStream
	      InputStream is = ...;
	      // copy it to response's OutputStream
	      org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
	      response.flushBuffer();
	    } catch (IOException ex) {
	      log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
	      throw new RuntimeException("IOError writing file to output stream");
	    }

	}*/
	
	
	
}
