package controller;

import dto.LoginDTO;
import dto.LoginResponseDTO;
import dto.RegistrationDTO;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import security.CustomUserDetailsService;
import security.TokenUtil;
import service.UserService;

import java.util.List;

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
    private CustomUserDetailsService customUserService;

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {

        User user = customUserService.findUserByUserName(loginDTO.getUsername());

        if (user == null || !loginDTO.getUsername().equals(user.getUsername())) {
            return  ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }

        String token = tokenUtils.generateToken(user.getUsername(), user.getRole().toString());
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setToken(token);
        return ResponseEntity.ok(responseDTO);
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

    @GetMapping(path = "/users")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userService.findAllByType();
        return new ResponseEntity<>(users, HttpStatus.OK);

    }

}
