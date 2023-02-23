package com.oli.HometownPolitician.domain.billUserRelation.repository;

import com.oli.HometownPolitician.global.argument.input.TargetSlicePaginationInput;
import com.querydsl.core.types.dsl.BooleanExpression;

import static com.oli.HometownPolitician.domain.billUserRelation.entity.QBillUserRelation.billUserRelation;

public class BillUserRelationRepositoryCond {
    public BooleanExpression notDeleted() {
        return billUserRelation.deletedAt.isNull();
    }
    public BooleanExpression notUnfollowed() {
        return billUserRelation.isUnfollowed.isFalse();
    }


    public Long billUserRelationLimit(TargetSlicePaginationInput pagination) {
        if (pagination == null) {
            return null;
        }
        return Long.valueOf(pagination.getElementSize());
    }

}
