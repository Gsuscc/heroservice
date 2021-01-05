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
public class Army {

    private List<Long> army;

    public boolean isDuplicate() {
        for (Long uniqueId: army) {
//            if (uniqueId != null && army.stream().allMatch((Long id) -> id ==))
            //todo
        }
        return false;
    }

}
