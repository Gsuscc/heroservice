package com.reactheroes.heroservice.service;

import com.reactheroes.heroservice.entity.Hero;
import com.reactheroes.heroservice.entity.Rarity;
import com.reactheroes.heroservice.model.IdGroup;
import com.reactheroes.heroservice.model.HeroPack;
import com.reactheroes.heroservice.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PackGenerator {
    private final Random random;
    private final List<Hero> common;
    private final List<Hero> uncommon;
    private final List<Hero> rare;
    private final List<Hero> epic;
    private final List<Hero> legendary;
    private final HeroRepository heroRepository;

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

    public List<Hero> getHeroesByIds(IdGroup idGroup) {
        List<Hero> heroes = new ArrayList<>();
        idGroup.getIds().forEach((id) -> heroes.add(heroRepository.findById(id).get()));
        return heroes;
    }

    public Hero getPack4Card() {
        final int LEGENDARY_CHANCE_RATE = 100000;
        final int EPIC_CHANCE_RATE = 3000;
        final int RARE_CHANCE_RATE = 400;
        return getHero(LEGENDARY_CHANCE_RATE, EPIC_CHANCE_RATE, RARE_CHANCE_RATE);
    }

    public Hero getPack6Card() {
        final int LEGENDARY_CHANCE_RATE = 10000;
        final int EPIC_CHANCE_RATE = 1000;
        final int RARE_CHANCE_RATE = 100;
        return getHero(LEGENDARY_CHANCE_RATE, EPIC_CHANCE_RATE, RARE_CHANCE_RATE);
    }

    public Hero getPack8Card() {
        final int LEGENDARY_CHANCE_RATE = 3000;
        final int EPIC_CHANCE_RATE = 200;
        final int RARE_CHANCE_RATE = 40;
        return getHero(LEGENDARY_CHANCE_RATE, EPIC_CHANCE_RATE, RARE_CHANCE_RATE);
    }

    private Hero getHero(int LEGENDARY_CHANCE_RATE, int EPIC_CHANCE_RATE, int RARE_CHANCE_RATE) {
        final int UNCOMMON_CHANCE_RATE = 2;
        if (0 == random.nextInt(LEGENDARY_CHANCE_RATE)) return getRandom(legendary);
        if (0 == random.nextInt(EPIC_CHANCE_RATE)) return getRandom(epic);
        if (0 == random.nextInt(RARE_CHANCE_RATE)) return getRandom(rare);
        if (0 == random.nextInt(UNCOMMON_CHANCE_RATE)) return getRandom(uncommon);
        return getRandom(common);
    }

    public HeroPack getPack4() {
        List<Hero> heroes = new ArrayList<>();
        while (heroes.size() < 4) {
            heroes.add(getPack4Card());
        }
        return new HeroPack(heroes);
    }

    public HeroPack getPack6() {
        List<Hero> heroes = new ArrayList<>();
        heroes.add(getRandom(rare));
        heroes.add(getRandom(rare));
        while (heroes.size() < 6) {
            heroes.add(getPack6Card());
        }
        return new HeroPack(heroes);
    }

    public HeroPack getPack8() {
        List<Hero> heroes = new ArrayList<>();
        heroes.add(getRandom(epic));
        heroes.add(getRandom(rare));
        heroes.add(getRandom(rare));
        while (heroes.size() < 8) {
            heroes.add(getPack8Card());
        }
        return new HeroPack(heroes);
    }

}
