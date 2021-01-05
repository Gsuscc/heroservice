package com.reactheroes.userservice.repository;

import com.reactheroes.userservice.entity.HeroCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface HeroCardRepository extends JpaRepository<HeroCard, Long> {

    Page<HeroCard> findAllByEmailIs(String email, Pageable pageable);

    Page<HeroCard> findAllByEmailIsAndIdIsAndUniqueIdNot(Pageable page, String email, Long id, Long uniqueId);

    @Modifying
    @Transactional
    @Query("UPDATE HeroCard hc SET hc.xp = :newXp WHERE hc.uniqueId = :uniqueId")
    void setHeroCardXp(@Param("newXp") Long newXp, @Param("uniqueId") Long uniqueId);

    @Transactional
    @Modifying
    void deleteByUniqueIdIs(Long uniqueId);

    Integer countAllByEmailIs(String email);

    boolean existsByEmailIsAndUniqueIdIs(String email, Long uniqueId);

}
