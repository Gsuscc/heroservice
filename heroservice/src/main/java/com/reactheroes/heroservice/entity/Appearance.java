package com.reactheroes.heroservice.entity;

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
    private Long id;
    private String gender;
    private String race;
    @ElementCollection
    private List<String> height;
    @ElementCollection
    private List<String> weight;
    private String eyeColor;
    private String hairColor;

    @OneToOne(mappedBy = "appearance")
    private Hero hero;

}
