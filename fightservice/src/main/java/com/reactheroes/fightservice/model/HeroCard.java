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
        Integer kapow = ((combat + strength) + (level * 50)) / 12;
        Integer boom = ((combat + intelligence) + (level * 50)) / 12;
        Integer doubleHit = ((combat + speed) + (level * 50)) / 12;
        Integer hit = ((speed + strength + power + combat) + (level * 50));
        Integer miss = ((speed + intelligence + durability + combat) + (level * 50)) / 4;

        this.stat = new Stat();
        this.stat.setMaxHp(maxHp);
        this.stat.setMaxDmg(maxDmg);
        this.stat.setMinDmg(minDmg);
        this.stat.setKapowRate(kapow);
        this.stat.setBoomRate(boom);
        this.stat.setDoubleRate(doubleHit);
        this.stat.setMissRate(miss);
        this.stat.setHitRate(hit);


    }

}


