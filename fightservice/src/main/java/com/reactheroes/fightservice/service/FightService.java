package com.reactheroes.fightservice.service;

import com.google.gson.Gson;
import com.reactheroes.fightservice.dao.interfaces.FightDao;
import com.reactheroes.fightservice.entity.FightCache;
import com.reactheroes.fightservice.model.Fight;
import com.reactheroes.fightservice.model.FightBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FightService {

    private final UserCallerService userCallerService;
    private final FightDao fightDao;
    private final Gson gson;

    @Autowired
    public FightService(UserCallerService userCallerService, FightDao fightDao, Gson gson) {
        this.userCallerService = userCallerService;
        this.fightDao = fightDao;
        this.gson = gson;
    }

    public FightBase getCachedFightByEmail(String email) {
        FightCache fightCache = fightDao.getFightByEmail(email);
        String enemyJsonString = fightCache.getEnemyCached();
        Fight fight = enemyJsonString == null ? null : gson.fromJson(enemyJsonString, Fight.class);
        return FightBase
                .builder()
                .myArmy(getArmyByEmail(email))
                .enemyArmy(fight)
                .build();
    }

    public Fight getArmyByEmail(String email) {
        return userCallerService.getArmyByEmail(email);
    }

}
