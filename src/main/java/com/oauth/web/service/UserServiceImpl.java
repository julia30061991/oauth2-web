package com.oauth.web.service;

import com.oauth.web.model.Role;
import com.oauth.web.model.User;
import com.oauth.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void createUser(String name, String email, String role) throws Exception {
        if(name == null || email == null || role == null) {
            throw new Exception("No one field cannot be empty");
        }
        User user = new User(name, email);
        switch (role) {
            case "ROLE_ADMIN":
                user.setRole(Role.ADMIN);
                break;
            case "ROLE_USER":
                user.setRole(Role.USER);
                break;
        }
        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUser(int id) throws Exception {
        User user = userRepository.findByUserId(id);
        if (user != null) {
            userRepository.deleteByUserId(id);
        } else {
            throw new Exception("User does not found");
        }
    }
}