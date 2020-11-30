package com.reactheroes.heroservice.setup;

import com.reactheroes.heroservice.entity.Hero;
import com.reactheroes.heroservice.repository.HeroRepository;
import com.reactheroes.heroservice.service.RemoteHeroCaller;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class InitDb {

    private final long HERO_COUNT = 731L;
    Logger logger = LoggerFactory.getLogger(InitDb.class);
    private final RemoteHeroCaller remoteHeroCaller;
    private final HeroRepository heroRepository;

    @Autowired
    public InitDb(RemoteHeroCaller remoteHeroCaller, HeroRepository heroRepository) {
        this.remoteHeroCaller = remoteHeroCaller;
        this.heroRepository = heroRepository;
    }

    @PostConstruct
    private void fillDatabase() {
        logger.info("Checking hero data availability... Please wait!");
        for (long i = 1L; i <= HERO_COUNT; i++) {
            try {
                if (!heroRepository.existsById(i)) {
                    Hero hero = remoteHeroCaller.downloadHero(i);
                    hero.calculateCost();
                    heroRepository.save(hero);
                    logger.info("Hero saved successfully: " + hero.getName());
                }
            } catch (Exception e) {
                logger.error("Error while saving hero (ID: " + i + "): " + e.getMessage());
            }
        }
        logger.info("Hero list is up to date!");
    }
}
