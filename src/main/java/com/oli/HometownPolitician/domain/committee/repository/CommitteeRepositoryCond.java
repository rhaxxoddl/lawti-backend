package com.oli.HometownPolitician.domain.committee.repository;

import com.oli.HometownPolitician.domain.committee.entity.QCommittee;
import com.oli.HometownPolitician.domain.committee.input.CommitteeInput;
import com.querydsl.core.types.dsl.BooleanExpression;

public class CommitteeRepositoryCond {

    public BooleanExpression eqCommitteeInput(CommitteeInput input) {
        if (input == null || input.getCommitteeId() == null)
            return null;
        return QCommittee.committee.id.eq(input.getCommitteeId());
    }
}
