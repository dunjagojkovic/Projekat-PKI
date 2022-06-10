package com.security.pki.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.pki.dto.CreateRootDTO;
import com.security.pki.dto.RegistrationDTO;
import com.security.pki.dto.UserDTO;
import com.security.pki.model.Certificate;
import com.security.pki.model.User;
import com.security.pki.repository.UserRepository;
import com.security.pki.security.SecurityUtils;

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
	PasswordEncoder encoder;

    public User getCurrentUser() {
        String username = SecurityUtils.getCurrentUserLogin().get();
        return userRepository.findByUsername(username).get();
    }

    public User registerUser(RegistrationDTO dto){

        Optional<User> optionalUser = userRepository.findByUsername(dto.getUsername());

        if(!optionalUser.isEmpty()) {
            return null;
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));
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
}
