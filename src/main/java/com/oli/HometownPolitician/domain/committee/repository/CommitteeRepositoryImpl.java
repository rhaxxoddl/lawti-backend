package com.oli.HometownPolitician.domain.committee.repository;

import com.oli.HometownPolitician.domain.committee.entity.Committee;
import static com.oli.HometownPolitician.domain.committee.entity.QCommittee.committee;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class CommitteeRepositoryImpl implements CommitteeRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final CommitteeRepositoryCond committeeCond;

    public CommitteeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.committeeCond = new CommitteeRepositoryCond();
    }
    @Override
    public List<Committee> qFindAll() {
        return queryFactory
                .selectFrom(committee)
                .where(
                        committeeCond.notDeleted()
                )
                .fetch();
    }
}
