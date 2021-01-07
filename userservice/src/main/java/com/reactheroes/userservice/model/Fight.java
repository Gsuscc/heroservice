package com.reactheroes.userservice.model;

import com.reactheroes.userservice.entity.HeroCard;
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

}
