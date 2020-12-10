package com.reactheroes.userservice.dao.interfaces;

import com.reactheroes.userservice.entity.HeroCard;
import com.reactheroes.userservice.entity.UserDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HeroCardDao {

    void gatherXp(Long amount, Long id);

    void addCard(HeroCard heroCard);
    Page<HeroCard> getHeroCardsPageForUser(UserDetail userDetail, Integer page);

}
