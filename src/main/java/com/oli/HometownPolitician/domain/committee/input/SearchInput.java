package com.oli.HometownPolitician.domain.committee.input;

import com.oli.HometownPolitician.domain.search.input.SearchFilterInput;
import com.oli.HometownPolitician.global.argument.input.TargetSlicePaginationInput;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class SearchInput {
    private String searchText;
    private SearchFilterInput filter;
    @NotNull(message = "pagination로 NULL이 들어올 수 없습니다")
    private TargetSlicePaginationInput pagination;
}
