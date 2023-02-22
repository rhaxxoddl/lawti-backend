package com.oli.HometownPolitician.domain.bill.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

import static com.oli.HometownPolitician.domain.bill.entity.QBill.bill;

public class BillRepositoryCond {
    public BooleanExpression billEqId(Long id) {
        if (id == null)
            return null;
        return bill.id.eq(id);
    }

    public BooleanExpression billNotDeleted() {
        return bill.deletedAt.isNull();
    }

    public Predicate billMatchTagByTagIdList(List<Long> tagIdList) {
        return bill.tags.any().tag.id.in(tagIdList);
    }
}
