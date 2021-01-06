package com.reactheroes.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appearance {


    private String gender;
    private String race;
    private List<String> height;
    private List<String> weight;

    private String eyeColor;

    private String hairColor;


}
