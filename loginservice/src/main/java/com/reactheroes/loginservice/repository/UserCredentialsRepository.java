package com.reactheroes.loginservice.repository;

import com.reactheroes.loginservice.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredential, Long> {

    Optional<UserCredential> findByEmail(String email);
    boolean existsByEmail(String email);
}
