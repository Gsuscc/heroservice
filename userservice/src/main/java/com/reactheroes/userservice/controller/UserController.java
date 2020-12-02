package com.reactheroes.userservice.controller;

import com.reactheroes.userservice.security.JwtTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    private final JwtTokenServices jwtTokenServices;

    @Autowired
    public UserController(JwtTokenServices jwtTokenServices) {
        this.jwtTokenServices = jwtTokenServices;
    }

    @GetMapping("/mydetails")
    private ResponseEntity<Object> getMyDetails(HttpServletRequest httpServletRequest) {
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        return ResponseEntity.ok(email);
    }

}
