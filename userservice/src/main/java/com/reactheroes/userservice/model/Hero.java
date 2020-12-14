package com.reactheroes.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hero {

    private Long id;
    private String name;
    private Powerstats powerstats;
    private Biography biography;
    private Appearance appearance;
    private Work work;
    private Connections connections;
    private Image image;
    private String rarity;
    private Integer level;
    private Long xp;
    private Long cardid;

    public Hero(Hero hero) {
        this.id = hero.getId();
        this.name = hero.getName();
        this.powerstats = hero.getPowerstats();
        this.biography = hero.getBiography();
        this.appearance = hero.getAppearance();
        this.work = hero.getWork();
        this.connections = hero.getConnections();
        this.image = hero.getImage();
        this.rarity = hero.getRarity();
        this.level = hero.getLevel();
        this.xp = hero.getXp();
        this.cardid = hero.getCardid();
    }

}
