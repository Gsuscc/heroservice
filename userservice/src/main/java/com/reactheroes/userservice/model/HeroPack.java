package com.reactheroes.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeroPack {

    List<Hero> heroes;

    public Hero getHeroById(Long id) {
        return heroes.stream().filter(hero -> hero.getId().equals(id)).findFirst().get();
    }

}
