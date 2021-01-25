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
public class Fight {

    String nick;
    List<HeroCard> cards;

    public void generateStats() {
        cards.forEach(HeroCard::calculateStats);
    }

}
