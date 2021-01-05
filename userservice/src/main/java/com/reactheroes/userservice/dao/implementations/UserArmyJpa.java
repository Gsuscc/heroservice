package com.reactheroes.userservice.dao.implementations;

import com.reactheroes.userservice.dao.interfaces.UserArmyDao;
import com.reactheroes.userservice.entity.UserArmy;
import com.reactheroes.userservice.model.Army;
import com.reactheroes.userservice.repository.UserArmyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserArmyJpa implements UserArmyDao {

    private final UserArmyRepository userArmyRepository;

    @Autowired
    public UserArmyJpa(UserArmyRepository userArmyRepository) {
        this.userArmyRepository = userArmyRepository;
    }

    @Override
    public void createArmy(UserArmy userArmy) {
        userArmyRepository.save(userArmy);
    }

    @Override
    public UserArmy getUserArmyByEmail(String email) {
        UserArmy userArmy = userArmyRepository.findFirstByEmailIs(email);
        if (userArmy == null) {
            userArmy = UserArmy
                    .builder()
                    .email(email)
                    .build();
            userArmyRepository.save(userArmy);
        }
        return userArmy;
    }

    @Override
    public void updateUserArmy(String email, Army army) {
        userArmyRepository.updateArmy(
                email,
                army.getArmy().get(0),
                army.getArmy().get(1),
                army.getArmy().get(2),
                army.getArmy().get(3),
                army.getArmy().get(4)
        );
    }

}
