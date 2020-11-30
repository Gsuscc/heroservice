package com.reactheroes.heroservice.entity;

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
    private Long id;

    private String occupation;

    @Column(columnDefinition = "TEXT")
    private String base;

    @OneToOne(mappedBy = "work")
    private Hero hero;

}
