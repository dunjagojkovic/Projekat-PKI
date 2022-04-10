package service;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

}
