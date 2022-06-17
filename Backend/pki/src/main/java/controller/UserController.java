package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dto.*;
import model.User;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import security.CustomUserDetailsService;
import security.TokenUtil;
import service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http:/localhost:4200", exposedHeaders = "token")
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtil tokenUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CustomUserDetailsService customUserService;

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
    	System.out.println(loginDTO);
    	
        User user = customUserService.findUserByUserName(loginDTO.getUsername());

        if (user == null || !loginDTO.getUsername().equals(user.getUsername()) || !user.isActivated()) {
        	System.out.println("User not found!");
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
            
        }
        
        if (loginDTO.getPassword()!=null
                && !bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
        	System.out.println("Wrong pass");
        	System.out.println(bCryptPasswordEncoder.encode(loginDTO.getPassword()));
        	return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
        else if(loginDTO.getCode()!=null
                && ((!bCryptPasswordEncoder.matches(loginDTO.getCode(), user.getLoginCode())) || LocalDateTime.now().isAfter(user.getLoginCodeValidity()))){
            System.out.println("Login code is not valid!");
            return  ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
        else {
        	System.out.println("Sve je proslo");
        	String token = tokenUtils.generateToken(user.getUsername(), user.getRole().toString());
            LoginResponseDTO responseDTO = new LoginResponseDTO();
            responseDTO.setToken(token);
            return ResponseEntity.ok(responseDTO);
        }

        
    }

    @GetMapping(path = "/current")
    public ResponseEntity<?> getCurrentUser() {
        return new ResponseEntity<>(userService.getCurrentUser(), HttpStatus.OK);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> registerClient(HttpServletRequest request, @RequestBody RegistrationDTO registrationDTO) {

        User user = userService.registerUser(request, registrationDTO);

        if(user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    
    @GetMapping(path = "/getAllUsers")
    @PreAuthorize("hasAuthority('getAllUsers')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.convertUsersToDTO(userService.getAllUsers()), HttpStatus.OK);
    }
    
    @GetMapping(path = "/getSubordinateUsers/{currentUserUsername}")
    @PreAuthorize("hasAuthority('getSubordinateUsers')")
    public ResponseEntity<List<UserDTO>> getSubordinateUsers(@PathVariable String currentUserUsername) {
        return new ResponseEntity<>(userService.convertUsersToDTO(userService.getAllSubordinateUsersToUser(userService.getUserByUsername(currentUserUsername))), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/checkActivationCode",consumes = "text/plain",produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> checkActivationCode(@RequestBody String c) throws JsonProcessingException {
        System.out.println("Activation code "+c);
        String code = c;
        if(userService.userAlreadyActivated(code)) {
            System.out.println("Already validated!");
            return ResponseEntity.ok("already validated");
        }

        boolean valid = userService.checkActivationCode(code);

        if(valid) {
            System.out.println("Valid!");
            return new ResponseEntity<String>("Valid", HttpStatus.OK);
        }

        else {
            System.out.println("Acivation code expired!");
            return ResponseEntity.ok("Acivation code expired!");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/checkForgottenPassword",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> resetPassword( @RequestBody ResetPasswordDTO dto) throws JsonProcessingException{
        System.out.println("Password reset code "+dto.getCode());
        String code = dto.getCode();


        if(!userService.resetCodeExists(code))
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        User user = userService.findByPasswordResetCode(code);


        boolean valid = userService.checkPasswordResetCode(dto);

        if(valid) return new ResponseEntity<String>(HttpStatus.OK);
        else {
            System.out.println("Expired!");
            return new ResponseEntity<String>(HttpStatus.GATEWAY_TIMEOUT);
        }
    }

    @PostMapping(path = "/forgottenpassword")
    public ResponseEntity<?> forgottenPassword(HttpServletRequest request, @RequestBody ForgottenPasswordDTO dto){
        User user = customUserService.findUserByUserName(dto.getUsername());
        if(user == null) {
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
        userService.forgottenPassword(user, request);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(path = "/loginCode")
    public ResponseEntity<?> getLoginCode(HttpServletRequest request, @RequestBody ForgottenPasswordDTO dto  )
    {
        System.out.println("Here I am");
        User user = customUserService.findUserByUserName(dto.getUsername());
        System.out.println(user);
        if(user == null) {
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
        userService.getLoginCode(user, request);
        return new ResponseEntity<String>("Code is sent to your email address!", HttpStatus.OK);
    }
}
