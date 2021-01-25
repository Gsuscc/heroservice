package com.reactheroes.fightservice.repository;

import com.reactheroes.fightservice.entity.EnemyQueue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnemyRepository extends JpaRepository<EnemyQueue, Long> {

    boolean existsByEmailIs(String email);

}
