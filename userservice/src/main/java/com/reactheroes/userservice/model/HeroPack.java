package com.reactheroes.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class HeroPack {
    List<Hero> heroes;

    public HeroPack() {
        this.heroes = new ArrayList<>();
    }

}
