package com.oli.HometownPolitician.domain.bill.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommitteeDto {
    private Long committeId;
    private String name;
}
