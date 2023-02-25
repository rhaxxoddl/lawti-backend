package com.oli.HometownPolitician.domain.search.contoller;

import com.oli.HometownPolitician.domain.committee.input.SearchInput;
import com.oli.HometownPolitician.domain.search.dto.SearchResultsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
public class SearchController {

    @QueryMapping(name = "search")
    public SearchResultsDto search(@Argument(name = "input") @Valid SearchInput input) {
        return null;
    }
}
