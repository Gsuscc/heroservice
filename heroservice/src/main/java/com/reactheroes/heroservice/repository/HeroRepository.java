package com.reactheroes.heroservice.repository;

import com.reactheroes.heroservice.entity.Hero;
import com.reactheroes.heroservice.entity.Rarity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HeroRepository extends JpaRepository<Hero, Long> {

    @Query("SELECT h FROM Hero h " +
            "WHERE lower(h.biography.fullName) " +
            "LIKE lower(concat('%', :value,'%')) " +
            "OR lower(h.name) " +
            "LIKE lower(concat('%', :value,'%'))")
    List<Hero> getByName(String value, Pageable page);

    @Query("SELECT h FROM Hero h " +
            "WHERE h.rarity = :rarity ORDER BY rand()")
    List<Hero> getByRarity(Rarity rarity);
}

