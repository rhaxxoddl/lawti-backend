package com.oli.HometownPolitician.domain.bill.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ProposersDto {
    private List<ProposerDto> proposers;
}
