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

}
