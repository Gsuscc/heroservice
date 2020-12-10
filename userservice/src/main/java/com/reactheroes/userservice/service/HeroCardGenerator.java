package com.reactheroes.userservice.service;

import com.reactheroes.userservice.entity.HeroCard;
import com.reactheroes.userservice.model.Hero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class HeroCardGenerator {
    private final HeroCallerService heroCallerService;

    @Autowired
    public HeroCardGenerator(HeroCallerService heroCallerService) {
        this.heroCallerService = heroCallerService;
    }

    private Integer getLevel(Long xp) {
        if (xp < 100L) return 1;
        if (xp < 400L) return 2;
        if (xp < 1500L) return 3;
        if (xp < 5000L) return 4;
        if (xp < 15000L) return 5;
        if (xp < 30000L) return 6;
        if (xp < 55000L) return 7;
        if (xp < 80000L) return 8;
        if (xp < 100000L) return 9;
        if (xp < 130000L) return 10;
        if (xp < 160000L) return 11;
        if (xp < 200000L) return 12;
        return 13;
    }

    private void generateUserCard(HeroCard heroCard) {
        Hero hero = heroCallerService.getHeroById(heroCard.getCardId());
        hero.setLevel(getLevel(heroCard.getXp()));
        hero.setXp(heroCard.getXp());
        hero.setCardid(heroCard.getId());
        heroCard.setHero(hero);
    }

    public void addCardInfosToUserCards(Page<HeroCard> heroCards) {
        heroCards.forEach(this::generateUserCard);
    }
}
