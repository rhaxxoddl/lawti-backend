package com.oli.HometownPolitician.domain.search.dto;

import com.oli.HometownPolitician.global.argument.output.SlicePaginationDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchResultsDto {
    private List<SearchResultDto> list;
    private SlicePaginationDto pagination;
}
