package com.reactheroes.userservice.dao.interfaces;

import com.reactheroes.userservice.entity.HeroCard;
import com.reactheroes.userservice.entity.UserDetail;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HeroCardDao {

    void gatherXp(Long amount, Long id);

    void addCard(HeroCard heroCard);
    Page<HeroCard> getHeroCardsPageForUser(UserDetail userDetail, Integer page);

    Page<HeroCard> getMergeableCards(int page, UserDetail userDetail , Long cardId, Long id);

    HeroCard getHeroCardByCardId(Long cardId);

    void deleteCard(Long cardId, UserDetail userDetail);
}
