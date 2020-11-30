package com.reactheroes.heroservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Long id;

    @Column(columnDefinition = "TEXT")
    @JsonProperty(value = "group-affiliation")
    private String groupAffiliation;

    @Column(columnDefinition = "TEXT")
    private String relatives;

    @OneToOne(mappedBy = "connections")
    @JsonBackReference
    private Hero hero;

}
