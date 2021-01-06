package com.reactheroes.userservice.service;

import com.reactheroes.userservice.entity.HeroCard;
import com.reactheroes.userservice.model.IdGroup;
import com.reactheroes.userservice.model.Hero;
import com.reactheroes.userservice.model.HeroPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HeroCardGenerator {
    private final HeroCallerService heroCallerService;

    @Autowired
    public HeroCardGenerator(HeroCallerService heroCallerService) {
        this.heroCallerService = heroCallerService;
    }

    private void buildHeroCard(HeroCard heroCard, Hero hero) {
        heroCard.addHeroData(hero);
        heroCard.setHeroLevel();
    }

    public void generateSingleCard(HeroCard heroCard){
        Hero hero = heroCallerService.getHeroById(heroCard.getId());
        buildHeroCard(heroCard, hero);
    }

    public void generateSingleCardOffline(HeroCard heroCard, Hero hero){
        buildHeroCard(heroCard, hero);
    }

    public void generateMultipleCards(Page<HeroCard> heroCards) {
        HeroPack heroPack = heroCallerService.getHeroesById(new IdGroup(heroCards));
        heroCards.forEach(heroCard -> buildHeroCard(heroCard, heroPack.getHeroById(heroCard.getId())));
    }

    public void generateMultipleCards(List<HeroCard> heroCards) {
        HeroPack heroPack = heroCallerService.getHeroesById(new IdGroup(heroCards));
        heroCards.forEach(heroCard -> buildHeroCard(heroCard, heroPack.getHeroById(heroCard.getId())));
    }
}
