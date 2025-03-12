package com.joe.controller;

import com.joe.model.User;
import com.joe.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final CustomUserDetailsService userDetailsService;

    public ResponseEntity<User> signUp(){
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
