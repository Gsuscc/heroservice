package com.reactheroes.userservice.controller;

import com.reactheroes.userservice.dao.interfaces.UserDetailDao;
import com.reactheroes.userservice.entity.HeroCard;
import com.reactheroes.userservice.security.JwtTokenServices;
import com.reactheroes.userservice.service.HeroCardService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
public class HeroCardController {

    private final HeroCardService heroCardService;
    private final JwtTokenServices jwtTokenServices;
    private final UserDetailDao userDetailDao;

    public HeroCardController(HeroCardService heroCardService, JwtTokenServices jwtTokenServices, UserDetailDao userDetailDao) {
        this.heroCardService = heroCardService;
        this.jwtTokenServices = jwtTokenServices;
        this.userDetailDao = userDetailDao;
    }

    @GetMapping("/mycards")
    private ResponseEntity<?> getMyCards(@RequestParam int page, HttpServletRequest httpServletRequest) {
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        Page<HeroCard> heroCards = heroCardService.getMyCards(email, page);
        return ResponseEntity.ok(heroCards);
    }

    @GetMapping("/buypack")
    private ResponseEntity<?> buyPackOfHeroes(HttpServletRequest httpServletRequest, @RequestParam int pack){
        if(pack != 4 && pack != 6 && pack !=8) {
            return new ResponseEntity<>("Invalid pack size", HttpStatus.BAD_REQUEST);
        }
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        try {
            if (pack == 4) userDetailDao.decrementBalance(1000L, email);
            if (pack == 6) userDetailDao.decrementBalance(3000L, email);
            if (pack == 8) userDetailDao.decrementBalance(8500L, email);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(heroCardService.getPack(pack, email));
    }

    @GetMapping("/merge")
    private ResponseEntity<?> getMergeableCards(HttpServletRequest httpServletRequest, @RequestParam Long uniqueId, @RequestParam Integer page){
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        HeroCard card = heroCardService.getCardByUniqueId(uniqueId, email);
        Page<HeroCard> mergeableCards = heroCardService.getMergeableCards(page, email, uniqueId, card.getId());
        return ResponseEntity.ok(mergeableCards);
    }

    @GetMapping("/mergecard")
    private ResponseEntity<?> mergeCards(HttpServletRequest httpServletRequest, @RequestParam Long mergeInto, @RequestParam Long merging){
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        return ResponseEntity.ok((heroCardService.mergeCards(email, mergeInto, merging)));
    }

    @DeleteMapping("/sell-card")
    private ResponseEntity<?> sellUserCard(HttpServletRequest httpServletRequest, @RequestBody Map<String, Long> request){
        String email = jwtTokenServices.getEmailFromToken(httpServletRequest);
        Long uniqueId = request.get("uniqueId");
        try {
            heroCardService.sellCard(email, uniqueId);
            userDetailDao.incrementBalance(heroCardService.calculateSellingPrice(uniqueId), email);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("Success");
    }

}
