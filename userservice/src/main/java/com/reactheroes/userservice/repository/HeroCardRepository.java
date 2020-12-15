package com.reactheroes.userservice.repository;

import com.reactheroes.userservice.entity.HeroCard;
import com.reactheroes.userservice.entity.UserDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import javax.transaction.Transactional;
import java.util.List;

public interface HeroCardRepository extends JpaRepository<HeroCard, Long> {

    Page<HeroCard> findAllByUserDetailIs(UserDetail userDetail, Pageable pageable);

    Page<HeroCard> findAllByUserDetailIsAndCardIdIsAndIdNot(Pageable page, UserDetail userDetail ,Long cardId, Long id);

    @Query("SELECT hc.xp FROM HeroCard hc WHERE hc.id = :id")
    Long getHeroCardXp(@Param("id") Long id);


    @Modifying
    @Transactional
    @Query("UPDATE HeroCard hc SET hc.xp = :xp WHERE hc.id = :id")
    void incrementHeroCardXp(@Param("xp") Long xp, @Param("id") Long id);


    @Transactional
    @Modifying
    void deleteByIdIsAndUserDetailIs(Long id, UserDetail userdetail);

}
