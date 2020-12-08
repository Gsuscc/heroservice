package com.reactheroes.heroservice.controller;

import com.reactheroes.heroservice.entity.Hero;
import com.reactheroes.heroservice.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
public class HeroController {


    public HeroRepository heroRepository;
    private final Random random;


    @Autowired
    public HeroController(HeroRepository heroRepository, Random random) {
        this.heroRepository = heroRepository;
        this.random = random;
    }



    @GetMapping("/get/{id}")
    public ResponseEntity<Hero> getHero(@PathVariable Long id){
        return ResponseEntity.ok(heroRepository.findById(id).get());
    }

    @GetMapping("/heroes")
    public ResponseEntity<Object> getHeroesPerPage(@RequestParam Integer page){
        return ResponseEntity.ok(heroRepository.findAll(PageRequest.of(page, 18)));
    }


    @GetMapping("/search")
    public ResponseEntity<Object> getHeroByName(@RequestParam String value){
        return ResponseEntity.ok(heroRepository.getByName(value, PageRequest.of(0,2)));
    }

    @GetMapping("/price")
    public ResponseEntity<?> getHeroPrice(@RequestParam Long card){
        Long price = heroRepository.getPrice(card);
        return ResponseEntity.ok(Map.of("price",price));
    }

    @GetMapping("/getrandom")
    public ResponseEntity<?> getRandomHero(@RequestParam int pack){
        Optional<Hero> hero = heroRepository.findById((long) (random.nextInt(731) + 1));
        return ResponseEntity.ok(hero);
    }

}


