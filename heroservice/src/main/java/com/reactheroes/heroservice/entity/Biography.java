package com.reactheroes.heroservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Biography {

    @Id
    @GeneratedValue
    private Long id;

    @JsonProperty(value = "full-name")
    private String fullName;

    @JsonProperty(value = "alter-egos")
    private String alterEgos;

    @Singular
    @ElementCollection
    private List<String> aliases;

    @JsonProperty(value = "place-of-birth")
    private String placeOfBirth;

    @JsonProperty(value = "first-appearance")
    private String firstAppearance;
    private String publisher;
    private String alignment;

    @OneToOne(mappedBy = "biography")
    private Hero hero;

}
