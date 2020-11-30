package com.reactheroes.heroservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Connections {

    @Id
    @GeneratedValue
    private Long id;

    private String groupAffiliation;
    private String relatives;

    @OneToOne(mappedBy = "connections")
    private Hero hero;

}
