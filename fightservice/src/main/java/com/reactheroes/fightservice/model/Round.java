package com.reactheroes.fightservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Round {

    Player attacker;
    Player defender;

    Integer damage;
    Action action;

}
