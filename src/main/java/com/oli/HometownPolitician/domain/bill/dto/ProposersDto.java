package com.oli.HometownPolitician.domain.bill.dto;

import com.oli.HometownPolitician.domain.proposer.entity.Proposer;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class ProposersDto {
    private List<ProposerDto> list;

    static public ProposersDto from(List<Proposer> proposers) {
        if (proposers == null || proposers.isEmpty())
            return null;
        return ProposersDto.builder()
                .list(
                        proposers
                                .stream()
                                .map(ProposerDto::from)
                                .collect(Collectors.toList())
                ).build();
    }
}
