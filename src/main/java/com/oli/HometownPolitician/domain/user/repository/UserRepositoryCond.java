package com.oli.HometownPolitician.domain.user.repository;

import com.querydsl.core.types.dsl.BooleanExpression;

import static com.oli.HometownPolitician.domain.user.entity.QUser.user;

public class UserRepositoryCond {
    public BooleanExpression userEqUuid(String uuid) {
        if (uuid == null)
            return null;
        return user.uuid.eq(uuid);
    }

    public BooleanExpression userNotDeleted() {
        return user.deletedAt.isNull();
    }
}
