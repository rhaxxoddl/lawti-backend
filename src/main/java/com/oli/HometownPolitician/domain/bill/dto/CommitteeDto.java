package com.oli.HometownPolitician.domain.bill.dto;

import com.oli.HometownPolitician.domain.committee.entity.Committee;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommitteeDto {
    private Long committeId;
    private String name;

    static public CommitteeDto from(Committee committee) {
        if (committee == null)
            return null;
        return CommitteeDto.builder()
                .committeId(committee.getId())
                .name(committee.getName())
                .build();
    }
}
