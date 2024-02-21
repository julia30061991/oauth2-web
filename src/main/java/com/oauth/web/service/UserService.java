package com.oauth.web.service;

import com.oauth.web.model.User;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    void createUser(String name, String email, String role) throws Exception;

    void deleteUser(int id) throws Exception;
}