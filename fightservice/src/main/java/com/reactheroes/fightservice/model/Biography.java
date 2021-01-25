package com.reactheroes.fightservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Biography {

    private String fullName;

    private String alterEgos;

    private List<String> aliases;

    private String placeOfBirth;

    private String firstAppearance;
    private String publisher;
    private String alignment;


}
