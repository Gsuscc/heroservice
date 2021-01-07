package com.reactheroes.fightservice.service;

import com.google.gson.Gson;
import com.reactheroes.fightservice.dao.interfaces.EnemyDao;
import com.reactheroes.fightservice.dao.interfaces.FightDao;
import com.reactheroes.fightservice.entity.EnemyQueue;
import com.reactheroes.fightservice.entity.FightCache;
import com.reactheroes.fightservice.model.Fight;
import com.reactheroes.fightservice.model.FightBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FightService {

    private final UserCallerService userCallerService;
    private final FightDao fightDao;
    private final EnemyDao enemyDao;
    private final Gson gson;
    private List<EnemyQueue> enemyQueue = new ArrayList();


    private String getNextInQueue(String email) {
        String enemyEmail = null;
        while (enemyEmail == null) {
            if (enemyQueue.size() < 1) {
                enemyQueue = enemyDao.getAllEnemies();
            }
            String enemy = this.enemyQueue.remove(0).getEmail();
            if (!enemy.equals(email)) {
                enemyEmail = enemy;
            }
        }
        return enemyEmail;
    }

    @Autowired
    public FightService(UserCallerService userCallerService, FightDao fightDao, EnemyDao enemyDao, Gson gson) {
        this.userCallerService = userCallerService;
        this.fightDao = fightDao;
        this.enemyDao = enemyDao;
        this.gson = gson;
    }

    public FightBase getCachedFightByEmail(String email) {
        FightCache fightCache = fightDao.getFightByEmail(email);
        if (!enemyDao.existByEmailIs(email)) {
            enemyDao.addToQueue(email);
        }
        String enemyJsonString = fightCache.getEnemyCached();
        Fight fight = enemyJsonString == null ? null : gson.fromJson(enemyJsonString, Fight.class);
        return FightBase
                .builder()
                .myArmy(getArmyByEmail(email))
                .enemyArmy(fight)
                .build();
    }

    public Fight getNextEnemy(String email) {
        Fight enemy = getArmyByEmail(getNextInQueue(email));
        String enemyJsonString = gson.toJson(enemy);
        fightDao.addEnemyToCache(email, enemyJsonString);
        return enemy;
    }

    private Fight getArmyByEmail(String email) {
        return userCallerService.getArmyByEmail(email);
    }

}
