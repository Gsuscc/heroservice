package com.reactheroes.heroservice.entity;

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

    private String fullName;
    private String alterEgos;

    @Singular
    @ElementCollection
    private List<String> aliases;

    private String placeOfBirth;
    private String firstAppearance;
    private String publisher;
    private String alignment;

    @OneToOne(mappedBy = "biography")
    private Hero hero;

}
