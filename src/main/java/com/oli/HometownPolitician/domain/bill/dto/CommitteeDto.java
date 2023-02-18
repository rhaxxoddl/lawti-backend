package com.oli.HometownPolitician.domain.bill.dto;

import com.oli.HometownPolitician.domain.committe.entity.Committee;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommitteeDto {
    private Long committeId;
    private String name;

    static public CommitteeDto from(Committee committeeEntity) {
        return CommitteeDto.builder()
                .committeId(committeeEntity.getId())
                .name(committeeEntity.getName())
                .build();
    }
}
