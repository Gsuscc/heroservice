package com.reactheroes.fightservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomService {

    private final Random random;

    @Autowired
    public RandomService(Random random) {
        this.random = random;
    }

    public Integer getRandomTo(Integer to) {
        return random.nextInt(to);
    }

    public boolean isRandomRateGreaterThan(Integer rate, Integer rateAgainst) {
        return getRandomTo(rate) > getRandomTo(rateAgainst);
    }

    public Integer getRandomDamage(Integer minHit, Integer maxHit) {
        int diff = maxHit - minHit;
        return random.nextInt(diff) + minHit;
    }

}
