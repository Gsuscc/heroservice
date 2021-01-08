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

    int maxHp;
    int minDmg;
    int maxDmg;
    double kapowChance;
    double boomChance;
    double missChance;
    double doubleChance;

}
