package com.reactheroes.userservice.dao.interfaces;

import com.reactheroes.userservice.entity.HeroCard;
import com.reactheroes.userservice.entity.UserDetail;

import java.util.Set;

public interface UserDetailDao {

    UserDetail getUserDetail(String email);
    boolean isUserDetailNotExist(String email);
    boolean isNickAlreadyTaken(String nick);
    void createNewUserDetail(String nick, String email);
    void incrementBalance(Long amount, String email);
    void decrementBalance(Long amount, String email);
    Set<HeroCard> getAllHeroCardsByUserEmail(String email);

}
