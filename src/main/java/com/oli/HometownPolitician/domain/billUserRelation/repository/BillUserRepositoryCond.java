package com.oli.HometownPolitician.domain.billUserRelation.repository;

import com.oli.HometownPolitician.domain.billMessage.input.BillMessageRoomFilterInput;
import com.querydsl.core.types.dsl.BooleanExpression;
import static com.oli.HometownPolitician.domain.billUserRelation.entity.QBillUserRelation.billUserRelation;

public class BillUserRepositoryCond {

    public BooleanExpression filter(BillMessageRoomFilterInput filterInput) {
        if (filterInput.getTagList() == null && filterInput.getTagList().size() == 0)
            return null;
        return null;
    }
}
