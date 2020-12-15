package com.reactheroes.userservice.service;

import com.reactheroes.userservice.dao.interfaces.HeroCardDao;
import com.reactheroes.userservice.entity.HeroCard;
import com.reactheroes.userservice.entity.UserDetail;
import com.reactheroes.userservice.model.Hero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeroCardService {

    private HeroCardDao heroCardDao;
    private HeroCardGenerator heroCardGenerator;

    @Autowired
    public HeroCardService(HeroCardDao heroCardDao, HeroCardGenerator heroCardGenerator){
        this.heroCardDao=heroCardDao;
        this.heroCardGenerator = heroCardGenerator;
    }

    public Hero mergeCards(UserDetail userDetail, Long mergeInto, Long merging){
        HeroCard cardToMergeIn = heroCardDao.getHeroCardByCardId(mergeInto);
        HeroCard mergingCard = heroCardDao.getHeroCardByCardId(merging);
        heroCardDao.gatherXp(100L +  mergingCard.getXp(), cardToMergeIn.getId());
        heroCardDao.deleteCard(mergingCard.getId(), userDetail);
        cardToMergeIn.setXp(100L +  mergingCard.getXp() + cardToMergeIn.getXp());
        heroCardGenerator.generateSingleCard(cardToMergeIn);
        return cardToMergeIn.getHero();
    }

}
