package com.reactheroes.userservice.dao.interfaces;

import com.reactheroes.userservice.entity.UserArmy;
import com.reactheroes.userservice.model.Army;

public interface UserArmyDao {

    void createArmy(UserArmy userArmy);
    UserArmy getUserArmyByEmail(String email);
    void updateUserArmy(String email, Army army);

}
