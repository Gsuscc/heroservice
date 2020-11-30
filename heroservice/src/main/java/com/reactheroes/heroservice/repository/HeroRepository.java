package com.reactheroes.heroservice.repository;

import com.reactheroes.heroservice.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRepository extends JpaRepository<Hero, Long> {

}
