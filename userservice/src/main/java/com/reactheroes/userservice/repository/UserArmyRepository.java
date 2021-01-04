package com.reactheroes.userservice.repository;

import com.reactheroes.userservice.entity.UserArmy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserArmyRepository extends JpaRepository<UserArmy, Long> {

    UserArmy findFirstByEmailIs(String email);

}
