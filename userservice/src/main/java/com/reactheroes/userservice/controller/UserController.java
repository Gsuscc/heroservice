package com.reactheroes.userservice.controller;

import com.reactheroes.userservice.dao.interfaces.HeroCardDao;
import com.reactheroes.userservice.dao.interfaces.UserDetailDao;
import com.reactheroes.userservice.entity.HeroCard;
import com.reactheroes.userservice.entity.UserDetail;
import com.reactheroes.userservice.model.Balance;
import com.reactheroes.userservice.model.Hero;
import com.reactheroes.userservice.model.HeroPack;
import com.reactheroes.userservice.model.Nick;
import com.reactheroes.userservice.security.JwtTokenServices;
import com.reactheroes.userservice.service.HeroCallerService;
import com.reactheroes.userservice.service.HeroCardGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class UserController {

    private final JwtTokenServices jwtTokenServices;
    private final UserDetailDao userDetailDao;
    private final HeroCallerService heroCallerService;
    private final HeroCardDao heroCardDao;
    private final HeroCardGenerator heroCardGenerator;

    @Autowired
    public UserController(JwtTokenServices jwtTokenServices, UserDetailDao userDetailDao, HeroCallerService heroCallerService, HeroCardDao heroCardDao, HeroCardGenerator heroCardGenerator) {
        this.jwtTokenServices = jwtTokenServices;
        this.userDetailDao = userDetailDao;
        this.heroCallerService = heroCallerService;
        this.heroCardDao = heroCardDao;
        this.heroCardGenerator = heroCardGenerator;
    }

    @GetMapping("/status")
    private ResponseEntity<?> getStatus(HttpServletRequest httpServletRequest) {
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        if (userDetailDao.isUserDetailNotExist(email)) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        return ResponseEntity.ok(userDetailDao.getNickByEmail(email));
    }

    @GetMapping("/mydetails")
    private ResponseEntity<Object> getMyDetails(HttpServletRequest httpServletRequest) {
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        if (userDetailDao.isUserDetailNotExist(email)) {
            return new ResponseEntity<>("Need to create first", HttpStatus.NOT_IMPLEMENTED);
        }
        return ResponseEntity.ok(userDetailDao.getUserDetail(email));
    }

    @GetMapping("/mycards")
    private ResponseEntity<?> getMyCards(HttpServletRequest httpServletRequest) {
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        if (userDetailDao.isUserDetailNotExist(email)) {
            return new ResponseEntity<>("Need to create first", HttpStatus.NOT_IMPLEMENTED);
        }
        return ResponseEntity.ok(
                heroCardGenerator.generateUserCards(
                        userDetailDao.getAllHeroCardsByUserEmail(email)
                )
        );
    }

    @GetMapping("/balance")
    private ResponseEntity<?> getBalance(HttpServletRequest httpServletRequest) {
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        return ResponseEntity.ok(new Balance(userDetailDao.getUserBalance(email)));
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

    @GetMapping("/buypack")
    private ResponseEntity<?> buyPackOfHeroes(HttpServletRequest httpServletRequest, @RequestParam int pack){
        if(pack != 3 && pack != 5 && pack !=7) {
            return new ResponseEntity<>("Invalid pack size", HttpStatus.BAD_REQUEST);
        }
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        UserDetail userDetail = userDetailDao.getUserDetail(email);
        HeroPack heroPack = heroCallerService.getRandomHero(pack);
        try {
            if (pack == 3) userDetailDao.decrementBalance(1500L, email);
            if (pack == 5) userDetailDao.decrementBalance(3000L, email);
            if (pack == 7) userDetailDao.decrementBalance(7500L, email);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        for (Hero hero: heroPack.getHeroes()) {
            HeroCard heroCard = HeroCard.builder().cardId(hero.getId()).xp(0L).userDetail(userDetail).build();
            heroCardDao.addCard(heroCard);
        }
        return ResponseEntity.ok(heroPack.getHeroes());
    }

}
