package com.oli.HometownPolitician.domain.billUserRelation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class BillUserRelationRepositoryImpl implements BillUserRelationRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public BillUserRelationRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
