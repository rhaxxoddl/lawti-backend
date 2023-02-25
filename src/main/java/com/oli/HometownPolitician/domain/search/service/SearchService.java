package com.oli.HometownPolitician.domain.search.service;

import com.oli.HometownPolitician.domain.committee.input.SearchInput;
import com.oli.HometownPolitician.domain.search.dto.SearchResultsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class SearchService {

    public SearchResultsDto search(SearchInput input) {
        return null;
    }
}
