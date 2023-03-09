package com.oli.HometownPolitician.domain.politician.repository;

import com.oli.HometownPolitician.domain.politician.entity.Politician;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import static com.oli.HometownPolitician.domain.politician.entity.QPolitician.politician;

public class PoliticianRepositoryImpl implements PoliticianRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final PoliticianRepositoryCond politicianCond;

    public PoliticianRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.politicianCond = new PoliticianRepositoryCond();
    }

    @Override
    public Politician queryPoliticiansByPolitician(Politician input) {
        return queryFactory.selectFrom(politician)
                .where(
                        politicianCond.eqChineseName(input.getChineseName())
                ).fetchOne();
    }
}
