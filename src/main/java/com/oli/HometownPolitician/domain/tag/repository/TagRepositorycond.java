package com.oli.HometownPolitician.domain.tag.repository;

import com.oli.HometownPolitician.domain.tag.dto.TagInput;
import com.querydsl.core.types.dsl.BooleanExpression;

import static com.oli.HometownPolitician.domain.bill.entity.QBill.bill;

public class TagRepositorycond {
    static public BooleanExpression tagEqTagInput(TagInput input) {
        if (input == null || input.getId() == null)
            return null;
        return bill.id.eq(input.getId());
    }
}
