package com.oli.HometownPolitician.domain.bill.dto;

import com.oli.HometownPolitician.domain.proposer.entity.Proposer;
import com.oli.HometownPolitician.domain.proposer.enumeration.ProposerRole;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProposerDto {
    private Long politicianId;
    private String name;
    private ProposerRole role;

    static public ProposerDto from(Proposer proposer) {
        return ProposerDto.builder()
                .politicianId(proposer.getPolitician().getId())
                .name(proposer.getPolitician().getName())
                .role(proposer.getProposerRole())
                .build();
    }
}
