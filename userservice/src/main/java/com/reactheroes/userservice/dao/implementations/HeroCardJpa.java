package com.reactheroes.userservice.dao.implementations;

import com.reactheroes.userservice.dao.interfaces.HeroCardDao;
import com.reactheroes.userservice.entity.HeroCard;
import com.reactheroes.userservice.repository.HeroCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class HeroCardJpa implements HeroCardDao {

    private final HeroCardRepository heroCardRepository;

    @Autowired
    public HeroCardJpa(HeroCardRepository heroCardRepository) {
        this.heroCardRepository = heroCardRepository;
    }

    @Override
    public void setHeroCardXp(Long newXp, Long uniqueId) {
        heroCardRepository.setHeroCardXp(newXp, uniqueId);
    }

    @Override
    public Page<HeroCard> getHeroCardsPageForUser(String email, Integer page) {
        return heroCardRepository.findAllByEmailIs(email, PageRequest.of(page, 18, Sort.by("xp").descending().and(Sort.by("uniqueId").ascending())));
    }

    @Override
    public Page<HeroCard> getMergeableCards(Integer page, String email, Long uniqueId, Long id) {
        return heroCardRepository.findAllByEmailIsAndIdIsAndUniqueIdNot(PageRequest.of(page, 18, Sort.by("xp").descending()), email, id, uniqueId);
    }

    @Override
    public HeroCard getHeroCardByUniqueId(Long uniqueId) {
        return heroCardRepository.findById(uniqueId).get();
    }

    @Override
    public void deleteCard(Long uniqueId, String email) {
        heroCardRepository.deleteByUniqueIdIs(uniqueId);
    }

    @Override
    public void addCard(HeroCard heroCard) {
        heroCardRepository.save(heroCard);
    }

}
