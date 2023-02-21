package com.oli.HometownPolitician.domain.bill.dto;

import com.oli.HometownPolitician.domain.proposer.entity.Proposer;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class ProposersDto {
    private List<ProposerDto> proposers;

    static public ProposersDto from(List<Proposer> proposerEntitys) {
        return ProposersDto.builder()
                .proposers(
                        proposerEntitys
                                .stream()
                                .map(ProposerDto::from)
                                .collect(Collectors.toList())
                ).build();
    }
}
