package com.reactheroes.userservice.repository;

import com.reactheroes.userservice.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<UserDetail, Long> {

    boolean existsByEmail(String email);
    boolean existsByNick(String nick);
    UserDetail findByEmailIs(String email);

    @Query("SELECT ud.balance FROM UserDetail ud WHERE ud.email = :email")
    Long getUserBalance(@Param("email") String email);

    @Transactional
    @Modifying
    @Query("UPDATE UserDetail ud SET ud.balance = :newBalance WHERE ud.email = :email")
    void setUserBalance(@Param("newBalance") Long newBalance, @Param("email") String email);



}
