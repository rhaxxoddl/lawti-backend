package com.oli.HometownPolitician.domain.proposer.repository;

import com.oli.HometownPolitician.domain.proposer.entity.Proposer;
import static com.oli.HometownPolitician.domain.proposer.entity.QProposer.proposer;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class ProposerRepositoryImpl implements ProposerRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ProposerRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Proposer> qFindByBillId(Long billId) {
        return queryFactory
                .selectFrom(proposer)
                .where(
                        proposer.bill.id.eq(billId)
                ).fetch();
    }
}
