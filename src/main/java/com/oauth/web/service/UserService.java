package com.oauth.web.service;

import com.oauth.web.model.User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    void createUser(String username, String name, String email, String role) throws Exception;

    void deleteUser(int id) throws Exception;

    String addRegistration(OAuth2AuthenticationToken token) throws Exception;
}