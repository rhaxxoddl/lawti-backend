package com.oli.HometownPolitician.domain.search.dto;

import com.oli.HometownPolitician.domain.bill.dto.CommitteeDto;
import com.oli.HometownPolitician.domain.bill.dto.ProposersDto;
import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.bill.entity.PopularityBill;
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

    static public SearchResultDto from(PopularityBill popularityBill) {
        return popularityBill == null ? null : SearchResultDto
                .builder()
                .title(popularityBill.getBill().getTitle())
                .committee(CommitteeDto.from(popularityBill.getBill().getCommittee()))
                .proposers(ProposersDto.from(popularityBill.getBill().getProposers()))
                .build();
    }
}
