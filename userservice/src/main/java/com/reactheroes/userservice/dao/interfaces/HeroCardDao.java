package com.reactheroes.userservice.dao.interfaces;

import com.reactheroes.userservice.entity.HeroCard;
import org.springframework.data.domain.Page;

public interface HeroCardDao {

    void addCard(HeroCard heroCard);

    void setHeroCardXp(Long newXp, Long uniqueId);

    Page<HeroCard> getHeroCardsPageForUser(String email, Integer page);

    Page<HeroCard> getMergeableCards(Integer page, String email , Long uniqueId, Long id);

    HeroCard getHeroCardByUniqueId(Long uniqueId);

    void deleteCard(Long uniqueId, String email);
}
