package com.reactheroes.userservice.controller;

import com.reactheroes.userservice.dao.interfaces.UserArmyDao;
import com.reactheroes.userservice.security.JwtTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserArmyController {

    private final UserArmyDao userArmyDao;
    private final JwtTokenServices jwtTokenServices;

    @Autowired
    public UserArmyController(UserArmyDao userArmyDao, JwtTokenServices jwtTokenServices) {
        this.userArmyDao = userArmyDao;
        this.jwtTokenServices = jwtTokenServices;
    }

    @GetMapping("/myarmy")
    private ResponseEntity<?> getMyArmy(HttpServletRequest httpServletRequest) {
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        return ResponseEntity.ok(userArmyDao.getUserArmyByEmail(email));
    }
}
