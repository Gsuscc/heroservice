package com.reactheroes.userservice.repository;

import com.reactheroes.userservice.entity.UserArmy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import javax.transaction.Transactional;

public interface UserArmyRepository extends JpaRepository<UserArmy, Long> {

    UserArmy findFirstByEmailIs(String email);

    @Modifying
    @Transactional
    @Query("UPDATE UserArmy ua SET ua.card1 = :card1, ua.card2 = :card2, ua.card3 = :card3, ua.card4 = :card4, ua.card5 = :card5 WHERE ua.email = :email")
    void updateArmy(@Param("email") String email,
                    @Param("card1") Long card1,
                    @Param("card2") Long card2,
                    @Param("card3") Long card3,
                    @Param("card4") Long card4,
                    @Param("card5") Long card5
    );

}
