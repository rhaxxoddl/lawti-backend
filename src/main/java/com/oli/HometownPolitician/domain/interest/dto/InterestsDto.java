package com.oli.HometownPolitician.domain.interest.dto;

import com.oli.HometownPolitician.domain.interest.entity.Interest;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class InterestsDto {
    private List<InterestDto> list;

    static public InterestsDto from(List<Interest> interestList) {
        return InterestsDto
                .builder()
                .list(interestList
                        .stream()
                        .map(InterestDto::from)
                        .collect(Collectors.toList())
                )
                .build();
    }

}
