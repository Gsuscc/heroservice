package com.reactheroes.userservice.service;

import com.reactheroes.userservice.model.CardPriceModel;
import com.reactheroes.userservice.model.Hero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HeroCallerService {

    private RestTemplate restTemplate;

    @Value("${hero-service.url}")
    private String BASE_URL;

    @Autowired
    public HeroCallerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Long getCardPrice(Long id){
        CardPriceModel body = restTemplate.getForEntity(BASE_URL + "price?card=" + id.toString(), CardPriceModel.class).getBody();
        return body.getPrice();
    }

    public Hero getRandomHero(int pack){
         return restTemplate.getForEntity(BASE_URL + "getrandom?pack=" + pack, Hero.class).getBody();
    }

    public Hero getHeroById(Long id){
        return restTemplate.getForEntity(BASE_URL + "get/" + id, Hero.class).getBody();
    }

}
