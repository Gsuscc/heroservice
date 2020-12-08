package com.reactheroes.userservice.dao.implementations;

import com.reactheroes.userservice.dao.interfaces.HeroCardDao;
import com.reactheroes.userservice.entity.HeroCard;
import com.reactheroes.userservice.repository.HeroCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HeroCardJpa implements HeroCardDao {

    private final HeroCardRepository heroCardRepository;

    @Autowired
    public HeroCardJpa(HeroCardRepository heroCardRepository) {
        this.heroCardRepository = heroCardRepository;
    }

    @Override
    public void gatherXp(Long amount, Long id) {
        Long currentXp = heroCardRepository.getHeroCardXp(id);
        heroCardRepository.incrementHeroCardXp(currentXp + amount, id);
    }

    @Override
    public void addCard(HeroCard heroCard) {
        heroCardRepository.save(heroCard);
    }
}
