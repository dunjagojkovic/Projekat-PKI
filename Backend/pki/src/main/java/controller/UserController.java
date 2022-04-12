package controller;

import dto.LoginDTO;
import dto.LoginResponseDTO;
import dto.UserDTO;
import model.User;

import java.util.List;

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
    
    @GetMapping(path = "/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.convertUsersToDTO(userService.getAllUsers()), HttpStatus.OK);
    }
    
    @GetMapping(path = "/getSubordinateUsers/{currentUserUsername}")
    public ResponseEntity<List<UserDTO>> getSubordinateUsers(@PathVariable String currentUserUsername) {
        return new ResponseEntity<>(userService.convertUsersToDTO(userService.getAllSubordinateUsersToUser(userService.getUserByUsername(currentUserUsername))), HttpStatus.OK);
    }
}
