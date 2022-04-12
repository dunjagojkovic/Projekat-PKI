package service;

import model.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.UserDTO;
import repository.UserRepository;
import security.SecurityUtils;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getCurrentUser() {
        String username = SecurityUtils.getCurrentUserLogin().get();
        return userRepository.findByUsername(username).get();
    }
    
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
