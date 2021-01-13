package com.reactheroes.fightservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reactheroes.fightservice.service.RandomService;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Random;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Player {

    Long uniqueId;
    Integer myHp;
    boolean isAttacker;
    @JsonIgnore
    private final RandomService randomService = new RandomService(new Random());

    public boolean isDied() {
        return myHp <= 0;
    }

    public Action getAction(HeroCard card, HeroCard defender) {
        Integer missRate = defender.getStat().getMissRate();
        Integer hitRate = card.getStat().getHitRate();
        if (randomService.isRandomRateGreaterThan(missRate, hitRate)) return Action.MISS;
        Integer kapowRate = card.getStat().getKapowRate();
        if (randomService.isRandomRateGreaterThan(kapowRate, missRate)) return Action.KAPOW;
        Integer boomRate = card.getStat().getBoomRate();
        if (randomService.isRandomRateGreaterThan(boomRate, missRate)) return Action.BOOM;
        Integer doubleHitRate = card.getStat().getDoubleRate();
        if (randomService.isRandomRateGreaterThan(doubleHitRate, missRate)) return Action.DOUBLE;
        return Action.POW;
    }

    public Integer getDamage(HeroCard card, Action action) {
        Integer randomDamage = randomService.getRandomDamage(
                card.getStat().getMinDmg(),
                card.getStat().getMaxDmg()
        );

        switch (action) {
            case MISS: return 0;
            case BOOM: return randomDamage * 2;
            case KAPOW: return randomDamage * 3;
            case DOUBLE:
            case POW:
                return randomDamage;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }
    }

    public void receiveDamage(Integer damage) {
        myHp -= damage;
        if (myHp < 0) {
            myHp = 0;
        }
    }
}
