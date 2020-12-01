package com.reactheroes.loginservice.controller;


import com.reactheroes.loginservice.dao.interfaces.UserCredentialsDao;
import com.reactheroes.loginservice.entity.UserCredential;
import com.reactheroes.loginservice.security.JwtTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    UserCredentialsDao userCredentialsDao;
    AuthenticationManager authenticationManager;
    JwtTokenServices jwtTokenServices;

    @Autowired
    public AuthController(UserCredentialsDao userCredentialsDao, AuthenticationManager authenticationManager, JwtTokenServices jwtTokenServices) {
        this.userCredentialsDao = userCredentialsDao;
        this.authenticationManager = authenticationManager;
        this.jwtTokenServices = jwtTokenServices;
    }


    @PostMapping("/register")
    private ResponseEntity<Object> registerUser(@RequestBody UserCredential userCredential){
        try {
            userCredentialsDao.registerUser(userCredential);
            return ResponseEntity.ok("Successfully registered");
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    private ResponseEntity<Object> login(@RequestBody UserCredential userCredential, HttpServletResponse response){
        try {
            String email = userCredential.getEmail();
            // authenticationManager.authenticate calls loadUserByUsername in CustomUserDetailsService
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, userCredential.getPassword()));
            String token = jwtTokenServices.createToken(email);

            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(7 * 24 * 60 * 60);
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return ResponseEntity.ok("Success");
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid username/password supplied", HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
