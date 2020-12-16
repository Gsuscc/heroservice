package com.reactheroes.userservice.controller;

import com.reactheroes.userservice.dao.interfaces.HeroCardDao;
import com.reactheroes.userservice.dao.interfaces.UserDetailDao;
import com.reactheroes.userservice.model.UserDetailFull;
import com.reactheroes.userservice.model.Nick;
import com.reactheroes.userservice.security.JwtTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserDetailController {

    private final JwtTokenServices jwtTokenServices;
    private final UserDetailDao userDetailDao;
    private final HeroCardDao heroCardDao;


    @Autowired
    public UserDetailController(JwtTokenServices jwtTokenServices, UserDetailDao userDetailDao, HeroCardDao heroCardDao) {
        this.jwtTokenServices = jwtTokenServices;
        this.userDetailDao = userDetailDao;
        this.heroCardDao = heroCardDao;
    }

    @GetMapping("/status")
    private ResponseEntity<?> getStatus(HttpServletRequest httpServletRequest) {
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        if (userDetailDao.isUserDetailNotExist(email)) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        return ResponseEntity.ok(userDetailDao.getUserDetail(email));
    }

    @GetMapping("/mydetails")
    private ResponseEntity<Object> getMyDetails(HttpServletRequest httpServletRequest) {
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        UserDetailFull userDetailFull = new UserDetailFull(userDetailDao.getUserDetail(email));
        userDetailFull.setAdditionalDetails(heroCardDao);
        return ResponseEntity.ok(userDetailFull);
    }

    @PostMapping("/create")
    private ResponseEntity<?> createUserDetails(@RequestBody Nick nick, HttpServletRequest httpServletRequest) {
        if (userDetailDao.isNickAlreadyTaken(nick.getNick())) {
            return new ResponseEntity<>("Nickname is already taken!", HttpStatus.IM_USED);
        }
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        userDetailDao.createNewUserDetail(nick.getNick(), email);
        return ResponseEntity.ok("Success");
    }

}
