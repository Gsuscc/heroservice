package com.reactheroes.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reactheroes.userservice.model.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeroCard {

    @Id
    @GeneratedValue
    private Long uniqueId;

    @ColumnDefault("0")
    private Long xp;

    @JsonIgnore
    @Column(nullable = false)
    private String email;

    private Long id;

    @Transient
    private String name;
    @Transient
    private Powerstats powerstats;
    @Transient
    private Biography biography;
    @JsonIgnore
    @Transient
    private Appearance appearance;
    @JsonIgnore
    @Transient
    private Work work;
    @JsonIgnore
    @Transient
    private Connections connections;
    @Transient
    private Image image;
    @Transient
    private String rarity;
    @Transient
    private Integer level;

    public void addHeroData(Hero hero) {
        this.name = hero.getName();
        this.powerstats = hero.getPowerstats();
        this.biography = hero.getBiography();
        this.appearance = hero.getAppearance();
        this.work = hero.getWork();
        this.connections = hero.getConnections();
        this.image = hero.getImage();
        this.rarity = hero.getRarity();
    }

    public void setHeroLevel() {
        this.level = getLevel(this.xp);
    }

    private Integer getLevel(Long xp) {
        if (xp < 100L) return 1;
        if (xp < 400L) return 2;
        if (xp < 1500L) return 3;
        if (xp < 5000L) return 4;
        if (xp < 15000L) return 5;
        if (xp < 30000L) return 6;
        if (xp < 55000L) return 7;
        if (xp < 80000L) return 8;
        if (xp < 100000L) return 9;
        if (xp < 130000L) return 10;
        if (xp < 160000L) return 11;
        if (xp < 200000L) return 12;
        return 13;
    }


}


