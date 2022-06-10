package service;

import dto.CreateRootDTO;
import dto.RegistrationDTO;
import dto.ResetPasswordDTO;
import model.Certificate;
import model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dto.UserDTO;
import repository.UserRepository;
import security.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CertificateService certService;
    @Autowired
    CodeService codeService;
    @Autowired
            MailService<String> mailService;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public User getCurrentUser() {
        String username = SecurityUtils.getCurrentUserLogin().get();
        return userRepository.findByUsername(username).get();
    }

    public User registerUser(RegistrationDTO dto){

        Optional<User> optionalUser = userRepository.findByUsername(dto.getUsername());

        if(!optionalUser.isEmpty()) {
            return optionalUser.get();
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        user.setType("Regular");

        return userRepository.save(user);
    }

    public List<User> findAllByType(){
        return userRepository.findAllByType("Regular");
    }

    public Optional<User> findByUsername (String username) {return userRepository.findByUsername(username);}


    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public List<User> getAllSubordinateUsersToUser(User user) {
    	List<Certificate> subordinateCertificates = certService.getSubordinateCertificates(user);
    	List<User> allSubordinateUsers =  new ArrayList<>();
    	allSubordinateUsers.add(user);
    	for(Certificate c : subordinateCertificates) 
    	{
    		if(c.getUser() != null && !allSubordinateUsers.contains(c.getUser()))
    		{
    			allSubordinateUsers.add(c.getUser());
    		}
    	}
    	allSubordinateUsers = allSubordinateUsers.stream()
			     .distinct()
			     .collect(Collectors.toList());
    	return allSubordinateUsers;
    }
    
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
    
    public List<UserDTO> convertUsersToDTO(List<User> userList) {
		List<UserDTO> DTOList = new ArrayList<UserDTO>();
		for(User u : userList) {
			DTOList.add(new UserDTO(u));
		}
		return DTOList;
	}


    public void getLoginCode(User user, HttpServletRequest request) {
        String loginCode = codeService.generateLoginCode(user);
        mailService.sendCodetToEmail(user.getEmail(), loginCode, getSiteURL(request));
        user.setLoginCode(bCryptPasswordEncoder.encode(loginCode));
        user.setLoginCodeValidity(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);
    }

    private String getSiteURL(HttpServletRequest request) {
        return request.getHeader("origin");
    }

    public void forgottenPassword(User user, HttpServletRequest request) {
        String resetCode = codeService.generatePasswordResetCode(user);
        mailService.sendLinkToResetPassword(user.getEmail(), resetCode, getSiteURL(request));
        user.setPasswordResetCode(bCryptPasswordEncoder.encode(resetCode));
        user.setPasswordResetCodeValidity(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);
    }

    public boolean checkPasswordResetCode(ResetPasswordDTO dto) {
        System.out.println("akt kod koji se trazi "+ dto.getCode());
        User u = findByPasswordResetCode(dto.getCode());
        if(u!=null && LocalDateTime.now().isBefore(u.getPasswordResetCodeValidity())) {
            u.setPasswordResetCode(null);
            u.setPasswordResetCodeValidity(null);
            u.setPassword(bCryptPasswordEncoder.encode(dto.getNewPassword()));
            userRepository.save(u);
            return true;
        }
        return false;
    }

    public User findByPasswordResetCode(String code) {
        List<User> allUsers = userRepository.findAll();
        User foundUser = null;
        for(User u : allUsers) {
            if(bCryptPasswordEncoder.matches(code, u.getPasswordResetCode())) {
                System.out.println("Maching!");
                foundUser = u;
            }
        }
        return foundUser;
    }

    public boolean resetCodeExists(String code) {
        User u = findByPasswordResetCode(code);
        return u!=null;
    }

    public boolean userAlreadyActivated(String code) {
        User user = findByActivation(code);
        System.out.println("Found user= "+user);
        return user!=null && user.isActivated();
    }

    private User findByActivation(String code) {
        List<User> allUsers = userRepository.findAll();
        User foundUser = null;
        for(User u : allUsers) {
            if(bCryptPasswordEncoder.matches(code, u.getActivationCode())) {
                System.out.println("Maching!");
                foundUser = u;
            }
        }
        return foundUser;
    }

    public boolean checkActivationCode(String code) {
        System.out.println("akt kod koji se trazi "+ code);
        User u = findByActivation(code);
        if(u!=null && LocalDateTime.now().isBefore(u.getActivationCodeValidity())) {
            u.setActivated(true);
            userRepository.save(u);
            return true;
        }
        return false;
    }
}
