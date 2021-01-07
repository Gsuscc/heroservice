package com.reactheroes.fightservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FightCache {

    @Id
    @GeneratedValue
    private Long uniqueId;

    @Column(nullable = false, unique = true)
    private String email;

    private String enemyCached;

}
