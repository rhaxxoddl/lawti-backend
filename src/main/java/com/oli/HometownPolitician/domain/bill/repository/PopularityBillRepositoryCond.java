package com.oli.HometownPolitician.domain.bill.repository;

import com.oli.HometownPolitician.global.argument.input.TargetSlicePaginationInput;
import com.querydsl.core.BooleanBuilder;

import static com.oli.HometownPolitician.domain.bill.entity.QPopularityBill.popularityBill;

public class PopularityBillRepositoryCond {


    public BooleanBuilder popularityBillDirection(TargetSlicePaginationInput pagination) {
        BooleanBuilder builder = new BooleanBuilder();
        if (pagination == null || pagination.getTarget() == null) {
            return builder.and(null);
        } else if (pagination.getIsAscending()) {
            return builder.and(popularityBill.id.gt(pagination.getTarget()));
        } else {
            return builder.and(popularityBill.id.lt(pagination.getTarget()));
        }
    }
}
