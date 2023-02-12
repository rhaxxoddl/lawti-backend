package com.oli.HometownPolitician.domain.tag.repository;

import com.oli.HometownPolitician.domain.user.repository.UserRepositoryCond;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class TagRepositoryImpl implements TagRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final UserRepositoryCond userCond;

    public TagRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.userCond = new UserRepositoryCond();
    }
}
