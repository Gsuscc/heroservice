package com.reactheroes.heroservice.model;

import com.reactheroes.heroservice.entity.Hero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeroPack {


    List<Hero> heroes;

}
