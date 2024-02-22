package com.oauth.web.service;

import com.oauth.web.model.Role;
import com.oauth.web.model.User;
import com.oauth.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
    public void createUser(String username, String name, String email, String role) throws Exception {
        if (username == null || name == null || email == null || role == null) {
            throw new Exception("No one field cannot be empty");
        }
        if (userRepository.existsUserByEmail(email)) {
            throw new Exception("This email was already registered");
        }
        User user = new User(username, name, email);
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

    @Override
    public String getUserInfo(OAuth2AuthenticationToken token) throws Exception {
        Object name = "";
        Object email = "";
        Map<String, Object> info = token.getPrincipal().getAttributes();
        for (String key : info.keySet()) {
            if (key.equals("name")) {
                name = info.get(key);
            }
            if (key.equals("email")) {
                email = info.get(key);
            }
        }
        Collection<GrantedAuthority> authorities = token.getAuthorities();
        List<GrantedAuthority> roles = new ArrayList<>(authorities);
        Object role = roles.get(0);
        String username = token.getName();
        createUser(username, name.toString(), email.toString(), role.toString());
        return "1. User name: " + name + " 2. User email: " + email + " 3. Role: " + role;
    }
}