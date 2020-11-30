package com.reactheroes.heroservice.controller;

import com.reactheroes.heroservice.entity.Hero;
import com.reactheroes.heroservice.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class HeroController {

    public HeroRepository heroRepository;

    @PostMapping("/add")
    public void saveVideo(@RequestBody Hero hero){
        System.out.println(hero);
        heroRepository.save(hero);
    }

    @GetMapping("/test")
    public String getTest() { return "Test Working!"; };


}


