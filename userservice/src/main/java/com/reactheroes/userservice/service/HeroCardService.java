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
            heroCardGenerator.generateSingleCardOffline(heroCard, hero);
            heroCardDao.addCard(heroCard);
            newCards.add(heroCard);
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
        heroCardDao.deleteCard(mergingCard.getUniqueId());
        heroCardGenerator.generateSingleCard(cardToMergeIn);
        return cardToMergeIn;
    }

    public Long sellCard(String email, Long uniqueId) {
        Long price = calculateSellingPrice(uniqueId);
        validateCardForUser(heroCardDao.getHeroCardByUniqueId(uniqueId), email);
        heroCardDao.deleteCard(uniqueId);
        return price;
    }

    private void validateCardForUser(HeroCard heroCard, String email) {
        if (!heroCard.getEmail().equals(email)) throw new IllegalStateException("Access denied");
    }

    public Long calculateSellingPrice(Long uniqueId){
        HeroCard card = heroCardDao.getHeroCardByUniqueId(uniqueId);
        heroCardGenerator.generateSingleCard(card);
        if (card.getRarity().equals("COMMON")) return 50L;
        if (card.getRarity().equals("UNCOMMON")) return 75L;
        if (card.getRarity().equals("RARE")) return 150L;
        if (card.getRarity().equals("EPIC")) return 300L;
        return 490L;
    }
}
