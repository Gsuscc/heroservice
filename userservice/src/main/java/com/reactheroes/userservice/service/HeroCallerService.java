package com.reactheroes.userservice.service;

import com.reactheroes.userservice.model.CardPriceModel;
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
        CardPriceModel body = restTemplate.getForEntity(BASE_URL + id.toString(), CardPriceModel.class).getBody();
        return body.getPrice();
    }


}
