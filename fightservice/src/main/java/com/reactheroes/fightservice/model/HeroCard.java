package com.reactheroes.fightservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeroCard {

    private Long uniqueId;
    private Long xp;
    private Long id;
    private String name;
    private Powerstats powerstats;
    private Biography biography;
    private Image image;
    private String rarity;
    private Integer level;

    private Stat stat;

    public void calculateStats() {
        Integer intelligence = powerstats.getIntelligence() == null ? 1 : powerstats.getIntelligence();
        Integer strength = powerstats.getStrength() == null ? 1 : powerstats.getStrength();
        Integer speed = powerstats.getSpeed() == null ? 1 : powerstats.getSpeed();
        Integer durability = powerstats.getDurability() == null ? 1 : powerstats.getDurability();
        Integer power = powerstats.getPower() == null ? 1 : powerstats.getPower();
        Integer combat = powerstats.getCombat() == null ? 1 : powerstats.getCombat();

        int maxHp = 10 + level / 10 * durability;
        int minDmg = 2 + level / 30 * power;
        int maxDmg = minDmg + 2 + level / 50 * strength;
        double kapow = (combat + strength) / 2.0 * (level * 0.2);
        double boom = (combat + intelligence) / 2.0 * (level * 0.2);
        double doubleHit = (combat + speed) / 2.0 * (level * 0.2);
        double miss = 100.0 / ((speed + intelligence + durability + combat) * level);

        this.stat = new Stat();
        this.stat.setMaxHp(maxHp);
        this.stat.setMaxDmg(maxDmg);
        this.stat.setMinDmg(minDmg);
        this.stat.setKapowChance(kapow);
        this.stat.setBoomChance(boom);
        this.stat.setDoubleChance(doubleHit);
        this.stat.setMissChance(miss);


    }

}


