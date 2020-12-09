package com.reactheroes.heroservice.service;

import com.reactheroes.heroservice.entity.Hero;
import com.reactheroes.heroservice.entity.Rarity;
import com.reactheroes.heroservice.model.HeroPack;
import com.reactheroes.heroservice.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PackGenerator {
    private final HeroRepository heroRepository;
    private final Random random;
    private final List<Hero> common;
    private final List<Hero> uncommon;
    private final List<Hero> rare;
    private final List<Hero> epic;
    private final List<Hero> legendary;

    @Autowired
    public PackGenerator(HeroRepository heroRepository, Random random) {
        this.heroRepository = heroRepository;
        this.common = heroRepository.getByRarity(Rarity.COMMON);
        this.uncommon = heroRepository.getByRarity(Rarity.UNCOMMON);
        this.rare = heroRepository.getByRarity(Rarity.RARE);
        this.epic = heroRepository.getByRarity(Rarity.EPIC);
        this.legendary = heroRepository.getByRarity(Rarity.LEGENDARY);
        this.random = random;
    }

    private Hero getRandom(List<Hero> heroes) {
        return heroes.get(random.nextInt(heroes.size()));
    }

    public Hero getPack3Card() {
        final int EPIC_CHANCE_RATE = 3000;
        final int RARE_CHANCE_RATE = 400;
        final int UNCOMMON_CHANCE_RATE = 3;
        if (0 == random.nextInt(EPIC_CHANCE_RATE)) return getRandom(epic);
        if (0 == random.nextInt(RARE_CHANCE_RATE)) return getRandom(rare);
        if (0 == random.nextInt(UNCOMMON_CHANCE_RATE)) return getRandom(uncommon);
        return getRandom(common);
    }

    public Hero getPack5Card() {
        final int LEGENDARY_CHANCE_RATE = 10000;
        final int EPIC_CHANCE_RATE = 1000;
        final int RARE_CHANCE_RATE = 100;
        final int UNCOMMON_CHANCE_RATE = 2;
        if (0 == random.nextInt(LEGENDARY_CHANCE_RATE)) return getRandom(legendary);
        if (0 == random.nextInt(EPIC_CHANCE_RATE)) return getRandom(epic);
        if (0 == random.nextInt(RARE_CHANCE_RATE)) return getRandom(rare);
        if (0 == random.nextInt(UNCOMMON_CHANCE_RATE)) return getRandom(uncommon);
        return getRandom(common);
    }

    public Hero getPack7Card() {
        final int LEGENDARY_CHANCE_RATE = 3000;
        final int EPIC_CHANCE_RATE = 200;
        final int RARE_CHANCE_RATE = 40;
        final int UNCOMMON_CHANCE_RATE = 2;
        if (0 == random.nextInt(LEGENDARY_CHANCE_RATE)) return getRandom(legendary);
        if (0 == random.nextInt(EPIC_CHANCE_RATE)) return getRandom(epic);
        if (0 == random.nextInt(RARE_CHANCE_RATE)) return getRandom(rare);
        if (0 == random.nextInt(UNCOMMON_CHANCE_RATE)) return getRandom(uncommon);
        return getRandom(common);
    }

    public HeroPack getPack3() {
        List<Hero> heroes = new ArrayList<>();
        while (heroes.size() < 3) {
            heroes.add(getPack3Card());
        }
        return new HeroPack(heroes);
    }

    public HeroPack getPack5() {
        List<Hero> heroes = new ArrayList<>();
        heroes.add(getRandom(rare));
        heroes.add(getRandom(rare));
        while (heroes.size() < 5) {
            heroes.add(getPack5Card());
        }
        return new HeroPack(heroes);
    }

    public HeroPack getPack7() {
        List<Hero> heroes = new ArrayList<>();
        heroes.add(getRandom(rare));
        heroes.add(getRandom(rare));
        heroes.add(getRandom(epic));
        while (heroes.size() < 7) {
            heroes.add(getPack7Card());
        }
        return new HeroPack(heroes);
    }

}
