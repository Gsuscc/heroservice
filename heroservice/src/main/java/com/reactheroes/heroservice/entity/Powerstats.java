package com.reactheroes.heroservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import java.util.Arrays;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Powerstats {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    private Integer intelligence;
    private Integer strength;
    private Integer speed;
    private Integer durability;
    private Integer power;
    private Integer combat;
    @OneToOne(mappedBy = "powerstats")
    @JsonBackReference
    private Hero hero;


    @JsonIgnore
    public List<Integer> getAllStats() {
        return Arrays.asList(
                getCombat(),
                getDurability(),
                getIntelligence(),
                getPower(),
                getSpeed(),
                getStrength()
        );
    }
}
