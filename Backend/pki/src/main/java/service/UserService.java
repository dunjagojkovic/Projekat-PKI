package service;

import dto.CreateRootDTO;
import dto.RegistrationDTO;
import model.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.UserDTO;
import repository.UserRepository;
import security.SecurityUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

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
        user.setPassword(dto.getPassword());
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
        return userRepository.findAll();
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