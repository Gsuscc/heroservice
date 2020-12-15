package com.reactheroes.userservice.model;
import com.reactheroes.userservice.entity.HeroCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdGroup {

    Set<Long> ids;

    public IdGroup(Page<HeroCard> heroCards) {
        ids = new HashSet<>();
        heroCards.forEach((heroCard -> ids.add(heroCard.getId())));
    }

    public IdGroup(List<HeroCard> heroCards) {
        ids = new HashSet<>();
        heroCards.forEach((heroCard -> ids.add(heroCard.getId())));
    }

}
