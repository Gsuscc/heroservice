package com.reactheroes.heroservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Connections {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "TEXT")
    @JsonProperty(value = "group-affiliation")
    private String groupAffiliation;

    @Column(columnDefinition = "TEXT")
    private String relatives;

    @OneToOne(mappedBy = "connections")
    private Hero hero;

}
