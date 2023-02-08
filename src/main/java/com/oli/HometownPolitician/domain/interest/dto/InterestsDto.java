package com.oli.HometownPolitician.domain.interest.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class InterestsDto {
    private List<InterestDto> list;

    static public InterestsDto from(List<InterestDto> interestDtoList) {
        return InterestsDto
                .builder()
                .list(interestDtoList)
                .build();
    }

}
