package com.oli.HometownPolitician.domain.billUserRelation.repository;

import com.querydsl.core.types.dsl.BooleanExpression;

import static com.oli.HometownPolitician.domain.bill.entity.QBill.bill;
import static com.oli.HometownPolitician.domain.billUserRelation.entity.QBillUserRelation.billUserRelation;

public class BillUserRelationCond {
    public BooleanExpression notDeleted() {
        return billUserRelation.deletedAt.isNull();
    }
    public BooleanExpression notUnfollowed() {
        return billUserRelation.isUnfollowed.isFalse();
    }

}
