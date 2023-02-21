package com.oli.HometownPolitician.domain.billUserRelation.repository;

import com.oli.HometownPolitician.domain.billMessage.input.BillMessageRoomListInput;
import com.oli.HometownPolitician.domain.billUserRelation.entity.BillUserRelation;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class BillUserRelationRepositoryImpl implements BillUserRelationRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public BillUserRelationRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public List<BillUserRelation> qFindByUserIdAndFilter(BillMessageRoomListInput input, Long userId) {
        return null;
    }
}
