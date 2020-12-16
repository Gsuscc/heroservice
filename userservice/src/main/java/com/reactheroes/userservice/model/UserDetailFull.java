package com.reactheroes.userservice.model;

import com.reactheroes.userservice.dao.interfaces.HeroCardDao;
import com.reactheroes.userservice.entity.UserDetail;
import lombok.Data;

@Data
public class UserDetailFull {

    private Long id;
    private String email;
    private String nick;
    private Long balance;
    private Integer totalCardsNumber;

    public UserDetailFull(UserDetail userDetail) {
        this.id = userDetail.getId();
        this.email = userDetail.getEmail();
        this.nick = userDetail.getNick();
        this.balance = userDetail.getBalance();
    }

    public void setAdditionalDetails(HeroCardDao heroCardDao) {
        totalCardsNumber = heroCardDao.countAllByEmailIs(this.getEmail());
    }
}
