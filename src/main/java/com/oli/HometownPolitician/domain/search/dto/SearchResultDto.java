package com.oli.HometownPolitician.domain.search.dto;

import com.oli.HometownPolitician.domain.bill.dto.CommitteeDto;
import com.oli.HometownPolitician.domain.bill.dto.ProposersDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchResultDto {
    private String title;
    private CommitteeDto committee;
    private ProposersDto proposers;
}
