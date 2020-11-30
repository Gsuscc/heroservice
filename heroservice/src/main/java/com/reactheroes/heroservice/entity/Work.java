package com.reactheroes.heroservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Work {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private String occupation;

    @Column(columnDefinition = "TEXT")
    private String base;

    @OneToOne(mappedBy = "work")
    @JsonBackReference
    private Hero hero;

}
