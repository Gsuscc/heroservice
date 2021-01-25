package com.reactheroes.fightservice.dao.implementations;

import com.reactheroes.fightservice.dao.interfaces.EnemyDao;
import com.reactheroes.fightservice.entity.EnemyQueue;
import com.reactheroes.fightservice.repository.EnemyRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnemyJpa implements EnemyDao {

    private final EnemyRepository enemyRepository;

    public EnemyJpa(EnemyRepository enemyRepository) {
        this.enemyRepository = enemyRepository;
    }

    @Override
    public List<EnemyQueue> getAllEnemies() {
        return enemyRepository.findAll();
    }

    @Override
    public void addToQueue(String email) {
        enemyRepository.save(EnemyQueue.builder().email(email).build());
    }

    @Override
    public void removeFromQueue(String email) {
        // TODO
    }

    @Override
    public boolean existByEmailIs(String email) {
        return enemyRepository.existsByEmailIs(email);
    }
}
