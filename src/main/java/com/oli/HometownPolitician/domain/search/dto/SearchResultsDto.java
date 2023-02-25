package com.oli.HometownPolitician.domain.search.dto;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.bill.entity.PopularityBill;
import com.oli.HometownPolitician.global.argument.input.TargetSlicePaginationInput;
import com.oli.HometownPolitician.global.argument.output.SlicePaginationDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchResultsDto {
    private List<SearchResultDto> list;
    private SlicePaginationDto pagination;

    static public SearchResultsDto fromByBills(List<Bill> bills, TargetSlicePaginationInput input) {
        return SearchResultsDto
                .builder()
                .list(
                        bills.stream()
                                .map(SearchResultDto::from)
                                .toList()
                )
                .pagination(SlicePaginationDto.from(input, bills.size()))
                .build();
    }
    static public SearchResultsDto fromByPopularityBills(List<PopularityBill> popularityBills, TargetSlicePaginationInput input) {
        return SearchResultsDto
                .builder()
                .list(
                        popularityBills.stream()
                                .map(SearchResultDto::from)
                                .toList()
                )
                .pagination(SlicePaginationDto.from(input, popularityBills.size()))
                .build();
    }
}
