package com.reactheroes.heroservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    private Powerstats powerstats;
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    private Biography biography;
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    private Appearance appearance;
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    private Work work;
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    private Connections connections;
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    @Enumerated(EnumType.STRING)
    private Rarity rarity;

    @JsonIgnore
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
