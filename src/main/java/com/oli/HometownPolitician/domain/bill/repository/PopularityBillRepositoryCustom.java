package com.oli.HometownPolitician.domain.bill.repository;

import com.oli.HometownPolitician.domain.bill.entity.PopularityBill;
import com.oli.HometownPolitician.domain.search.input.SearchInput;

import java.util.List;

public interface PopularityBillRepositoryCustom {
    List<PopularityBill> queryPopularityBillsBySearchInput(SearchInput input);
}
