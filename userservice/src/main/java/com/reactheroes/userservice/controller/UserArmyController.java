package com.reactheroes.userservice.controller;

import com.reactheroes.userservice.dao.interfaces.HeroCardDao;
import com.reactheroes.userservice.dao.interfaces.UserArmyDao;
import com.reactheroes.userservice.dao.interfaces.UserDetailDao;
import com.reactheroes.userservice.entity.HeroCard;
import com.reactheroes.userservice.entity.UserArmy;
import com.reactheroes.userservice.model.Army;
import com.reactheroes.userservice.model.Fight;
import com.reactheroes.userservice.security.JwtTokenServices;
import com.reactheroes.userservice.service.HeroCardGenerator;
import com.reactheroes.userservice.service.HeroCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserArmyController {

    private final UserArmyDao userArmyDao;
    private final HeroCardDao heroCardDao;
    private final UserDetailDao userDetailDao;
    private final JwtTokenServices jwtTokenServices;
    private final HeroCardService heroCardService;
    private final HeroCardGenerator heroCardGenerator;

    @Autowired
    public UserArmyController(UserArmyDao userArmyDao, HeroCardDao heroCardDao, UserDetailDao userDetailDao, JwtTokenServices jwtTokenServices, HeroCardService heroCardService, HeroCardGenerator heroCardGenerator) {
        this.userArmyDao = userArmyDao;
        this.heroCardDao = heroCardDao;
        this.userDetailDao = userDetailDao;
        this.jwtTokenServices = jwtTokenServices;
        this.heroCardService = heroCardService;
        this.heroCardGenerator = heroCardGenerator;
    }

    @GetMapping("/myarmy")
    private ResponseEntity<?> getMyArmy(HttpServletRequest httpServletRequest) {
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        UserArmy userArmy = userArmyDao.getUserArmyByEmail(email);
        //TODO fix this internal server error when user dont have army or deleted
        List<HeroCard> army = List.of(heroCardService.getCardByUniqueId(userArmy.getCard1(), email),
                heroCardService.getCardByUniqueId(userArmy.getCard2(), email),
                heroCardService.getCardByUniqueId(userArmy.getCard3(), email),
                heroCardService.getCardByUniqueId(userArmy.getCard4(), email),
                heroCardService.getCardByUniqueId(userArmy.getCard5(), email));
        heroCardGenerator.generateMultipleCards(army);
        return ResponseEntity.ok(army);
    }

    @PostMapping("/setarmy")
    private ResponseEntity<?> setMyArmy(@RequestBody Army army, HttpServletRequest httpServletRequest) {
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        if(army.isDuplicate()) return new ResponseEntity<>("No duplicates allowed", HttpStatus.BAD_REQUEST);
        for (Long uniqueId: army.getArmy()) {
            if (!heroCardDao.isUserOwnCard(email, uniqueId))
                return new ResponseEntity<>("Got illegal card", HttpStatus.BAD_REQUEST);
        }
        userArmyDao.updateUserArmy(email, army);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/getarmy")
    private ResponseEntity<?> getArmy(HttpServletRequest httpServletRequest, @RequestParam String email) {
        UserArmy userArmy = userArmyDao.getUserArmyByEmail(email);
        List<HeroCard> army = List.of(
                heroCardService.getCardByUniqueId(userArmy.getCard1(), email),
                heroCardService.getCardByUniqueId(userArmy.getCard2(), email),
                heroCardService.getCardByUniqueId(userArmy.getCard3(), email),
                heroCardService.getCardByUniqueId(userArmy.getCard4(), email),
                heroCardService.getCardByUniqueId(userArmy.getCard5(), email));
        heroCardGenerator.generateMultipleCards(army);
        Fight fight = Fight
                .builder()
                .cards(army)
                .nick(userDetailDao.getNickByEmail(email).getNick())
                .build();
        return ResponseEntity.ok(fight);
    }
}
