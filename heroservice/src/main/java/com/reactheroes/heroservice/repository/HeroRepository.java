package com.reactheroes.heroservice.repository;

import com.reactheroes.heroservice.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRepository extends JpaRepository<Hero, Long> {

    @Query("SELECT h FROM Hero h " +
            "WHERE lower(h.biography.fullName) " +
            "LIKE lower(concat('%', :value,'%')) " +
            "OR lower(h.name) " +
            "LIKE lower(concat('%', :value,'%'))")
    List<Hero> getByName(String value, Pageable page);
}
