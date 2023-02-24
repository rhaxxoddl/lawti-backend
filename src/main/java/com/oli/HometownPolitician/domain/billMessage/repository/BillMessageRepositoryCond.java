package com.oli.HometownPolitician.domain.billMessage.repository;

import com.oli.HometownPolitician.global.argument.input.TargetSlicePaginationInput;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import static com.oli.HometownPolitician.domain.billMessage.entity.QBillMessage.billMessage;

public class BillMessageRepositoryCond {
    public BooleanExpression notDeleted() {
        return billMessage.deletedAt.isNull();
    }

    public Long billMessageLimit(TargetSlicePaginationInput pagination) {
        if (pagination == null) {
            return null;
        }
        return Long.valueOf(pagination.getElementSize());
    }

    public BooleanBuilder billMessageDirection(TargetSlicePaginationInput pagination) {
        BooleanBuilder builder = new BooleanBuilder();
        if (pagination == null || pagination.getTarget() == null) {
            return builder.and(null);
        } else if (pagination.getIsAscending()) {
            return builder.and(billMessage.id.gt(pagination.getTarget()));
        } else {
            return builder.and(billMessage.id.lt(pagination.getTarget()));
        }
    }
}
