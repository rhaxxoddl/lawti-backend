package com.oli.HometownPolitician.domain.bill.repository;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.search.input.SearchInput;

import java.util.List;

public interface BillRepositoryCustom {
    List<Bill> queryBillsByIdList(List<Long> idList);
    List<Bill> queryBillsBySearchInput(SearchInput input);
}
