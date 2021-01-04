package com.reactheroes.userservice.dao.interfaces;

import com.reactheroes.userservice.entity.UserArmy;

public interface UserArmyDao {

    void createArmy(UserArmy userArmy);
    UserArmy getUserArmyByEmail(String email);
    void updateUserArmy(String email, UserArmy userArmy);

}
