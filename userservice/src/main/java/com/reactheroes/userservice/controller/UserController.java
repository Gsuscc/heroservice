package com.reactheroes.userservice.controller;

import com.reactheroes.userservice.dao.interfaces.HeroCardDao;
import com.reactheroes.userservice.dao.interfaces.UserDetailDao;
import com.reactheroes.userservice.entity.HeroCard;
import com.reactheroes.userservice.entity.UserDetail;
import com.reactheroes.userservice.model.Hero;
import com.reactheroes.userservice.model.Nick;
import com.reactheroes.userservice.security.JwtTokenServices;
import com.reactheroes.userservice.service.HeroCallerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final JwtTokenServices jwtTokenServices;
    private final UserDetailDao userDetailDao;
    private final HeroCallerService heroCallerService;
    private final HeroCardDao heroCardDao;

    @Autowired
    public UserController(JwtTokenServices jwtTokenServices, UserDetailDao userDetailDao, HeroCallerService heroCallerService, HeroCardDao heroCardDao) {
        this.jwtTokenServices = jwtTokenServices;
        this.userDetailDao = userDetailDao;
        this.heroCallerService = heroCallerService;
        this.heroCardDao = heroCardDao;
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

    @PostMapping("/create")
    private ResponseEntity<?> createUserDetails(@RequestBody Nick nick, HttpServletRequest httpServletRequest) {
        if (userDetailDao.isNickAlreadyTaken(nick.getNick())) {
            return new ResponseEntity<>("Nickname is already taken!", HttpStatus.IM_USED);
        }
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        userDetailDao.createNewUserDetail(nick.getNick(), email);
        return ResponseEntity.ok("Success");
    }


    @GetMapping("/buyhero")
    private ResponseEntity<?> buyHero(HttpServletRequest httpServletRequest, @RequestParam Long id){
        Long cardPrice = heroCallerService.getCardPrice(id);
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        Long balance = userDetailDao.getUserBalance(email);
        if (balance >= cardPrice){
            UserDetail userDetail = userDetailDao.getUserDetail(email);
            userDetailDao.decrementBalance(cardPrice, email);
            HeroCard heroCard = HeroCard.builder().cardId(id).xp(0L).userDetail(userDetail).build();
            heroCardDao.addCard(heroCard);
            return ResponseEntity.ok("Success");
        }
         return new ResponseEntity<>("Not enough funds", HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/buypack")
    private ResponseEntity<?> buyPackOfHeroes(HttpServletRequest httpServletRequest, @RequestParam int pack){
        if(pack != 3 && pack != 5 && pack !=7) {
            return new ResponseEntity<>("Invalid pack size", HttpStatus.BAD_REQUEST);
        }
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        Long balance = userDetailDao.getUserBalance(email);
        List<Hero> randomHeroes = new ArrayList<>();
        UserDetail userDetail = userDetailDao.getUserDetail(email);
        for (int i = 0; i < pack ; i++) {
            Hero randomHero = heroCallerService.getRandomHero(pack);
            randomHeroes.add(randomHero);
            HeroCard heroCard = HeroCard.builder().cardId(randomHero.getId()).xp(0L).userDetail(userDetail).build();
            heroCardDao.addCard(heroCard);
        }
        return ResponseEntity.ok(randomHeroes);
    }

}
