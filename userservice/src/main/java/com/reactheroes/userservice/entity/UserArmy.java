package com.reactheroes.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserArmy {

    @Id
    @GeneratedValue
    private Long uniqueId;

    @Column(nullable = false, unique = true)
    private String email;

    private Long card1;
    private Long card2;
    private Long card3;
    private Long card4;
    private Long card5;

    public List<Long> getCards() {
        return List.of(card1, card2, card3, card4, card5);
    }

    public boolean isEmpty() {
        return card1 == null || card2 == null || card3 == null || card4 == null || card5 == null;
    }
}
