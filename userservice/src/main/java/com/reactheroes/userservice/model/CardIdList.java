package com.reactheroes.userservice.model;
import com.reactheroes.userservice.entity.HeroCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardIdList {

    Set<Long> cardIds;

    public CardIdList(Page<HeroCard> heroCards) {
        cardIds = new HashSet<>();
        heroCards.forEach((heroCard -> cardIds.add(heroCard.getCardId())));
    }

    @Override
    public String toString() {
        return "[" + cardIds.stream().map(Object::toString).collect(Collectors.joining(", ")) + ']';
    }
}
