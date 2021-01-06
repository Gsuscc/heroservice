package com.reactheroes.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Army {

    private List<Long> army;

    public boolean isDuplicate() {
        Set<Long> set = new HashSet<>();
        for (Long uniqueId: army) {
            if (uniqueId != null && !set.add(uniqueId)){
                return true;
            }
        }
        return false;
    }

}
