package com.reactheroes.fightservice.repository;

import com.reactheroes.fightservice.entity.FightCache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FightRepository extends JpaRepository<FightCache, Long> {

    FightCache findByEmailIs(String email);
}
