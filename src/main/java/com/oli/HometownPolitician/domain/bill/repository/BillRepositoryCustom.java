package com.oli.HometownPolitician.domain.bill.repository;

import com.oli.HometownPolitician.domain.bill.entity.Bill;

import java.util.List;

public interface BillRepositoryCustom {
    List<Bill> queryBillsByIdList(List<Long> idList);
}
