package com.reactheroes.userservice.service;

import com.reactheroes.userservice.dao.interfaces.HeroCardDao;
import com.reactheroes.userservice.entity.HeroCard;
import com.reactheroes.userservice.model.Hero;
import com.reactheroes.userservice.model.HeroPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class HeroCardService {

    private HeroCardDao heroCardDao;
    private HeroCardGenerator heroCardGenerator;
    private HeroCallerService heroCallerService;

    @Autowired
    public HeroCardService(HeroCardDao heroCardDao, HeroCardGenerator heroCardGenerator, HeroCallerService heroCallerService){
        this.heroCardDao=heroCardDao;
        this.heroCardGenerator = heroCardGenerator;
        this.heroCallerService = heroCallerService;
    }

    public HeroCard getCardByUniqueId(Long uniqueId, String email) {
        HeroCard heroCard = heroCardDao.getHeroCardByUniqueId(uniqueId);
        validateCardForUser(heroCard, email);
        return heroCard;
    }

    public Page<HeroCard> getMyCards(String email, Integer page) {
        Page<HeroCard> cards = heroCardDao.getHeroCardsPageForUser(email, page);
        heroCardGenerator.generateMultipleCards(cards);
        return cards;
    }

    public Page<HeroCard> getMergeableCards(Integer page, String email , Long uniqueId, Long id) {
        Page<HeroCard> heroCards = heroCardDao.getMergeableCards(page, email, uniqueId, id);
        heroCardGenerator.generateMultipleCards(heroCards);
        return heroCards;
    }

    public List<HeroCard> getPack(Integer pack, String email) {
        HeroPack heroPack = heroCallerService.getRandomHero(pack);
        List<HeroCard> newCards = new ArrayList<>();
        for (Hero hero: heroPack.getHeroes()) {
            HeroCard heroCard = HeroCard.builder()
                    .id(hero.getId())
                    .xp(0L)
                    .email(email)
                    .build();
            heroCardDao.addCard(heroCard);
            heroCardGenerator.generateMultipleCards(newCards);
        }
        return newCards;
    }

    public HeroCard mergeCards(String email, Long mergeInto, Long merging){
        HeroCard cardToMergeIn = heroCardDao.getHeroCardByUniqueId(mergeInto);
        HeroCard mergingCard = heroCardDao.getHeroCardByUniqueId(merging);
        validateCardForUser(cardToMergeIn, email);
        validateCardForUser(mergingCard, email);
        cardToMergeIn.setXp(100L + mergingCard.getXp() + cardToMergeIn.getXp());
        heroCardDao.setHeroCardXp(cardToMergeIn.getXp(), cardToMergeIn.getUniqueId());
        heroCardDao.deleteCard(mergingCard.getId(), email);
        heroCardGenerator.generateSingleCard(cardToMergeIn);
        return cardToMergeIn;
    }

    private void validateCardForUser(HeroCard heroCard, String email) {
        if (!heroCard.getEmail().equals(email)) throw new IllegalStateException("Access denied");
    }
}
