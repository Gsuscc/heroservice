package com.reactheroes.fightservice.controller;

import com.reactheroes.fightservice.dao.interfaces.FightDao;
import com.reactheroes.fightservice.entity.FightCache;
import com.reactheroes.fightservice.model.Fight;
import com.reactheroes.fightservice.model.FightBase;
import com.reactheroes.fightservice.security.JwtTokenServices;
import com.reactheroes.fightservice.service.FightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FightController {

    private final JwtTokenServices jwtTokenServices;
    private final FightService fightService;

    @Autowired
    public FightController(FightDao fightDao, JwtTokenServices jwtTokenServices, FightService fightService) {
        this.jwtTokenServices = jwtTokenServices;
        this.fightService = fightService;
    }

    @GetMapping("/getfight")
    private ResponseEntity<?> getFight(HttpServletRequest httpServletRequest) {
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        FightBase cachedFightByEmail = fightService.getCachedFightByEmail(email);
        return ResponseEntity.ok(cachedFightByEmail);
    }

    @GetMapping("/getenemy")
    private ResponseEntity<?> getEnemy(HttpServletRequest httpServletRequest) {
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        Fight enemy = fightService.getNextEnemy(email);
        return ResponseEntity.ok(enemy);
    }

}
