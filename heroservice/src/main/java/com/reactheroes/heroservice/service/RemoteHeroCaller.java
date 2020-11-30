package com.reactheroes.heroservice.service;

import com.reactheroes.heroservice.entity.Hero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RemoteHeroCaller {

    private final RestTemplate restTemplate;

    @Value("${hero.publicapi.url}")
    private String baseUrl;

    @Autowired
    public RemoteHeroCaller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Hero downloadHero(Long id) {
        return restTemplate.getForEntity(baseUrl + id, Hero.class).getBody();
    }

}
