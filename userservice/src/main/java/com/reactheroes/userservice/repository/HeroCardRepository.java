package com.reactheroes.userservice.repository;

import com.reactheroes.userservice.entity.HeroCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HeroCardRepository extends JpaRepository<HeroCard, Long> {

    @Query("SELECT hc.xp FROM HeroCard hc WHERE hc.id = :id")
    Long getHeroCardXp(@Param("id") Long id);

    @Modifying
    @Query("UPDATE HeroCard hc SET hc.xp = :xp WHERE hc.id = :id")
    void incrementHeroCardXp(@Param("xp") Long xp, @Param("id") Long id);

}
