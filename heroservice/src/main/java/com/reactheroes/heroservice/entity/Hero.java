package com.reactheroes.heroservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hero {

    @Id
    private Long id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private Powerstats powerstats;
    @OneToOne(cascade = CascadeType.ALL)
    private Biography biography;
    @OneToOne(cascade = CascadeType.ALL)
    private Appearance appearance;
    @OneToOne(cascade = CascadeType.ALL)
    private Work work;
    @OneToOne(cascade = CascadeType.ALL)
    private Connections connections;
    @OneToOne(cascade = CascadeType.ALL)
    private Image image;
    private Integer cost;

    public void calculateCost() {
        //TODO
    }

}
