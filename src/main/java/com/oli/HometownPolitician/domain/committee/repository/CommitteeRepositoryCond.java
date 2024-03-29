package com.oli.HometownPolitician.domain.committee.repository;

import com.querydsl.core.types.dsl.BooleanExpression;

import static com.oli.HometownPolitician.domain.committee.entity.QCommittee.committee;

public class CommitteeRepositoryCond {
    public BooleanExpression notDeleted() {
        return committee.deletedAt.isNull();
    }
    public BooleanExpression eqExternalCommitteeId(String externalCommitteeId) {
        if (externalCommitteeId == null)
            return null;
        return committee.externalCommitteeId.eq(externalCommitteeId);
    }
}
