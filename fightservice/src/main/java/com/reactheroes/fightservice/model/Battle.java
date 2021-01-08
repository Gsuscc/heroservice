package com.reactheroes.fightservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Battle {

    private Fight myArmy;
    private Fight enemyArmy;

    List<Round> rounds;

    public void startBattle() {
        myArmy.generateStats();
        enemyArmy.generateStats();
    }

}
