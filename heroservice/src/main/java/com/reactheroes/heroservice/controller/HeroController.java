package com.reactheroes.heroservice.controller;

import com.reactheroes.heroservice.entity.Hero;
import com.reactheroes.heroservice.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class HeroController {


    public HeroRepository heroRepository;

    @Autowired
    public HeroController(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }


    @GetMapping("/hero")
    public ResponseEntity<Hero> getHero(@RequestParam Long id){
        return ResponseEntity.ok(heroRepository.findById(id).get());
    }

    @GetMapping("/heroes")
    public ResponseEntity<Object> getHeroesPerPage(@RequestParam Integer page){
        return ResponseEntity.ok(heroRepository.findAll(PageRequest.of(page, 10)));
    }


    @GetMapping("/search")
    public ResponseEntity<Object> getHeroByName(@RequestParam String value){
//        return null;
        return ResponseEntity.ok(heroRepository.getByName(value, PageRequest.of(0,2)));
    }


}


