package com.oli.HometownPolitician.domain.bill.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PoliticianDto {

    private Long politicainId;
    private String name;
}
