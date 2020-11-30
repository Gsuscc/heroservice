package com.reactheroes.heroservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appearance {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    private String gender;
    private String race;
    @ElementCollection
    private List<String> height;
    @ElementCollection
    private List<String> weight;

    @JsonProperty(value = "eye-color")
    private String eyeColor;

    @JsonProperty(value = "hair-color")
    private String hairColor;

    @JsonBackReference
    @OneToOne(mappedBy = "appearance")
    private Hero hero;

}
