package com.oli.HometownPolitician.domain.billMessage.repository;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.bill.repository.BillRepositoryCustom;

import java.util.List;

public class BillMessageRepositoryImpl implements BillRepositoryCustom {
    @Override
    public List<Bill> queryBillsByIdList(List<Long> idList) {
        return null;
    }
}
