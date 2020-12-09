package com.reactheroes.userservice.service;

import com.reactheroes.userservice.model.CardPriceModel;
import com.reactheroes.userservice.model.Hero;
import com.reactheroes.userservice.model.HeroPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class HeroCallerService {

    private RestTemplate restTemplate;

    @Value("${hero-service.url}")
    private String BASE_URL;

    @Autowired
    public HeroCallerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public HeroPack getRandomHero(int pack){
        Hero[] heroes = restTemplate.getForEntity(BASE_URL + "getrandom?pack=" + pack, Hero[].class).getBody();
        return new HeroPack(Arrays.asList(heroes));
//        return restTemplate.getForObject(BASE_URL + "getrandom?pack=" + pack, HeroPack.class);
    }

    public Hero getHeroById(Long id){
        return restTemplate.getForEntity(BASE_URL + "get/" + id, Hero.class).getBody();
    }

}
