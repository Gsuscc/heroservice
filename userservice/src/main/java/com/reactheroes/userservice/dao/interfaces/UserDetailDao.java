package com.reactheroes.userservice.dao.interfaces;

import com.reactheroes.userservice.entity.HeroCard;
import com.reactheroes.userservice.entity.UserDetail;
import com.reactheroes.userservice.model.Nick;

import java.util.List;

public interface UserDetailDao {

    UserDetail getUserDetail(String email);
    boolean isUserDetailNotExist(String email);
    boolean isNickAlreadyTaken(String nick);
    Nick getNickByEmail(String email);
    void createNewUserDetail(String nick, String email);
    Long getUserBalance(String email);
    void incrementBalance(Long amount, String email);
    void decrementBalance(Long amount, String email);
}
