package com.reactheroes.heroservice.controller;

import com.reactheroes.heroservice.entity.Hero;
import com.reactheroes.heroservice.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
public class HeroController {


    public HeroRepository heroRepository;

    @Autowired
    public HeroController(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
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

}


