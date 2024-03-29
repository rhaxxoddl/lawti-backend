package com.oli.HometownPolitician.domain.search.service;

import com.oli.HometownPolitician.domain.bill.repository.BillRepository;
import com.oli.HometownPolitician.domain.search.dto.SearchResultsDto;
import com.oli.HometownPolitician.domain.search.input.SearchInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class SearchService {
    private final BillRepository billRepository;

    public SearchResultsDto search(SearchInput input) {
        return SearchResultsDto.fromByBills(billRepository.queryBillsBySearchInput(input), input.getPagination());
    }
}
