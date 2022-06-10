package com.security.pki.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.security.pki.dto.LoginDTO;
import com.security.pki.dto.LoginResponseDTO;
import com.security.pki.dto.RegistrationDTO;
import com.security.pki.dto.UserDTO;
import com.security.pki.jwt.JwtResponse;
import com.security.pki.jwt.JwtUtils;
import com.security.pki.model.User;
import com.security.pki.security.TokenUtil;
import com.security.pki.service.UserDetailsImpl;
import com.security.pki.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "token")
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtil tokenUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
	AuthenticationManager authenticationManager;
    
    @Autowired
	JwtUtils jwtUtils;

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginRequest) {
    	Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		User user = userService.findByUsername(loginRequest.getUsername()).orElse(null);
		if(user == null)
			return new ResponseEntity<>(
				      "User not authenticated or does not exist!", 
				      HttpStatus.NOT_FOUND);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = null;
		if(userDetails.getAuthorities() != null) {
			roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		}
		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
    }

    @GetMapping(path = "/current")
    public ResponseEntity<?> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(auth, HttpStatus.OK);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> registerClient(@RequestBody RegistrationDTO registrationDTO) {

        User user = userService.registerUser(registrationDTO);

        if(user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    
    @GetMapping(path = "/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.convertUsersToDTO(userService.getAllUsers()), HttpStatus.OK);
    }
    
    @GetMapping(path = "/getSubordinateUsers/{currentUserUsername}")
    public ResponseEntity<List<UserDTO>> getSubordinateUsers(@PathVariable String currentUserUsername) {
        return new ResponseEntity<>(userService.convertUsersToDTO(userService.getAllSubordinateUsersToUser(userService.getUserByUsername(currentUserUsername))), HttpStatus.OK);
    }
}
