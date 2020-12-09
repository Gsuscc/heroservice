package com.reactheroes.heroservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hero {

    @Id
    private Long id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private Powerstats powerstats;
    @OneToOne(cascade = CascadeType.ALL)
    private Biography biography;
    @OneToOne(cascade = CascadeType.ALL)
    private Appearance appearance;
    @OneToOne(cascade = CascadeType.ALL)
    private Work work;
    @OneToOne(cascade = CascadeType.ALL)
    private Connections connections;
    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    @Enumerated(EnumType.STRING)
    private Rarity rarity;

    @Transient
    private Integer cost;

    public void calculateCost() {
        cost = 10 + name.length() + powerstats.getAllStats()
                .stream()
                .reduce(0, (mem, element) -> mem += element == null ? 1 : element > 90 ? element * 3 : element);
    }

    public Rarity calculateRarity() {
        if (this.cost >= 1300) return Rarity.LEGENDARY;
        if (this.cost >= 798) return Rarity.EPIC;
        if (this.cost >= 500) return Rarity.RARE;
        if (this.cost >= 300) return Rarity.UNCOMMON;
        return Rarity.COMMON;
    }

}
