package com.reactheroes.fightservice.dao.interfaces;

import com.reactheroes.fightservice.entity.FightCache;

public interface FightDao {

    FightCache getFightByEmail(String email);
    void addEnemyToCache(String email, String enemyJsonString);

}
