package com.reactheroes.userservice.controller;

import com.reactheroes.userservice.dao.interfaces.HeroCardDao;
import com.reactheroes.userservice.dao.interfaces.UserArmyDao;
import com.reactheroes.userservice.model.Army;
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
public class UserArmyController {

    private final UserArmyDao userArmyDao;
    private final HeroCardDao heroCardDao;
    private final JwtTokenServices jwtTokenServices;

    @Autowired
    public UserArmyController(UserArmyDao userArmyDao, HeroCardDao heroCardDao, JwtTokenServices jwtTokenServices) {
        this.userArmyDao = userArmyDao;
        this.heroCardDao = heroCardDao;
        this.jwtTokenServices = jwtTokenServices;
    }

    @GetMapping("/myarmy")
    private ResponseEntity<?> getMyArmy(HttpServletRequest httpServletRequest) {
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        return ResponseEntity.ok(userArmyDao.getUserArmyByEmail(email));
    }

    @PostMapping("/setarmy")
    private ResponseEntity<?> setMyArmy(@RequestBody Army army, HttpServletRequest httpServletRequest) {
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        for (Long uniqueId: army.getArmy()) {
            if (!heroCardDao.isUserOwnCard(email, uniqueId))
                return new ResponseEntity<>("Got illegal card", HttpStatus.BAD_REQUEST);
        }
        userArmyDao.updateUserArmy(email, army);
        return ResponseEntity.ok("Success");
    }


}
