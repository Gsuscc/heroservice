package com.reactheroes.fightservice.dao.implementations;

import com.reactheroes.fightservice.dao.interfaces.FightDao;
import com.reactheroes.fightservice.entity.FightCache;
import com.reactheroes.fightservice.repository.FightRepository;
import org.springframework.stereotype.Component;

@Component
public class FightJpa implements FightDao {

    private final FightRepository fightRepository;

    public FightJpa(FightRepository fightRepository) {
        this.fightRepository = fightRepository;
    }


    @Override
    public FightCache getFightByEmail(String email) {
        FightCache fightCache = fightRepository.findByEmailIs(email);
        if (fightCache == null) {
            fightCache = FightCache.builder().email(email).build();
            fightRepository.save(
                    fightCache
            );
        }
        return fightCache;
    }
}
