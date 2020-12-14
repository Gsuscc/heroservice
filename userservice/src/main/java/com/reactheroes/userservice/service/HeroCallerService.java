package com.reactheroes.userservice.service;

import com.google.gson.Gson;
import com.reactheroes.userservice.model.CardIdList;
import com.reactheroes.userservice.model.Hero;
import com.reactheroes.userservice.model.HeroPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Set;

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
        return restTemplate.getForEntity(BASE_URL + "getrandom?pack=" + pack, HeroPack.class).getBody();
    }

    public Hero getHeroById(Long id){
        return restTemplate.getForEntity(BASE_URL + "get/" + id, Hero.class).getBody();
    }

    public HeroPack getHeroesById(CardIdList cardIds) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();
        String gsonString = gson.toJson(cardIds, CardIdList.class);
        HttpEntity<String> request = new HttpEntity<>(gsonString, headers);
        ResponseEntity<HeroPack> response = restTemplate.postForEntity( BASE_URL + "getheroes", request, HeroPack.class );
        return response.getBody();
    }

}
