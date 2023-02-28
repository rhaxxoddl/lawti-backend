package com.oli.HometownPolitician.domain.search.dto;

import com.oli.HometownPolitician.domain.bill.dto.CommitteeDto;
import com.oli.HometownPolitician.domain.bill.dto.ProposersDto;
import com.oli.HometownPolitician.domain.bill.entity.Bill;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchResultDto {
    private String title;
    private CommitteeDto committee;
    private ProposersDto proposers;

    static public SearchResultDto from(Bill bill) {
        return bill == null ? null : SearchResultDto
                .builder()
                .title(bill.getTitle())
                .committee(CommitteeDto.from(bill.getCommittee()))
                .proposers(ProposersDto.from(bill.getProposers()))
                .build();
    }
}
