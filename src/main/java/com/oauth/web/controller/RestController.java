package com.oauth.web.controller;

import com.oauth.web.model.User;
import com.oauth.web.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/") //доступно без аутентификации
    public String unidentifiedUser() {
        return "You are an unidentified user";
    }

    @PreAuthorize("hasAuthority('ROLE_USER')") //доступно аутентифицированному пользователю user
    @GetMapping("/info") //краткая информация и запись в бд
    public String getUserInfo(OAuth2AuthenticationToken token) throws Exception {
        try {
            return userService.getUserInfo(token);
        } catch (Exception e) {
            return e.getMessage();
        }
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