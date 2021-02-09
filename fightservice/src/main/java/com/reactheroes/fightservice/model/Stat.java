package com.reactheroes.fightservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stat {

    private int maxHp;
    private int minDmg;
    private int maxDmg;
    private Integer kapowRate;
    private Integer boomRate;
    private Integer doubleRate;
    private Integer missRate;
    private Integer hitRate;

}
