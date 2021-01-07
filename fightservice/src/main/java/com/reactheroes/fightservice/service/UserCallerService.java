package com.reactheroes.fightservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserCallerService {

    private final RestTemplate restTemplate;

    @Value("${user-service.url}")
    private String BASE_URL;

    @Autowired
    public UserCallerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
