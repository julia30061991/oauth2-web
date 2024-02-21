package com.oauth.web.controller;

import com.oauth.web.model.User;
import com.oauth.web.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private final UserServiceImpl userService;

    public RestController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/") //доступно любому пользователю
    public String unidentifiedUser() {
        return "You are an unidentified user";
    }

    @GetMapping("/info") //доступно аутентифицированному пользователю, краткая информация и запись в бд
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
        userService.createUser(name.toString(), email.toString(), role.toString());
        return "1. User name: " + name + " 2. User email: " + email + " 3. Role: " + role;
    }

    @PreAuthorize("hasAuthority('ROLE_USER')") //доступно аутентифицированному пользователю user
    @GetMapping("/show")
    public ResponseEntity<Object> getAll() {
        try {
            List<User> list = userService.getAllUsers();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')") //доступно аутентифицированному пользователю admin
    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}