package com.reactheroes.userservice.dao.interfaces;

import com.reactheroes.userservice.entity.HeroCard;

public interface HeroCardDao {

    void gatherXp(Long amount, Long id);

    void buyCard(HeroCard heroCard);

}
