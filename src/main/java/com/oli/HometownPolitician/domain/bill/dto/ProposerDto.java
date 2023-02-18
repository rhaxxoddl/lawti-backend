package com.oli.HometownPolitician.domain.bill.dto;

import com.oli.HometownPolitician.domain.proposer.enumeration.ProposerRole;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProposerDto {
    private Long politicianId;
    private String name;
    private ProposerRole role;
}
