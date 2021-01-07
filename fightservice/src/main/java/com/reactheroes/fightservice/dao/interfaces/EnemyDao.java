package com.reactheroes.fightservice.dao.interfaces;

import com.reactheroes.fightservice.entity.EnemyQueue;
import java.util.List;

public interface EnemyDao {
    List<EnemyQueue> getAllEnemies();
    void addToQueue(String email);
    void removeFromQueue(String email);
    boolean existByEmailIs(String email);
}
