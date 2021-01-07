package com.reactheroes.fightservice.repository;

import com.reactheroes.fightservice.entity.FightCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface FightRepository extends JpaRepository<FightCache, Long> {

    FightCache findByEmailIs(String email);

    @Transactional
    @Modifying
    @Query("UPDATE FightCache fc SET fc.enemyCached = :enemy WHERE fc.email = :email")
    void updateFightCache(@Param("email") String email, @Param("enemy") String enemyJsonString);

}
