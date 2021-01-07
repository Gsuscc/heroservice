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

}


