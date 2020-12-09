package com.reactheroes.heroservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.reactheroes.heroservice.util.StringListConverter;
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
    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> height;
    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> weight;

    @JsonProperty(value = "eye-color")
    private String eyeColor;

    @JsonProperty(value = "hair-color")
    private String hairColor;

    @JsonBackReference
    @OneToOne(mappedBy = "appearance")
    private Hero hero;

}
