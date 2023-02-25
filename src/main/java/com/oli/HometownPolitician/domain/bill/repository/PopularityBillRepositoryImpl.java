package com.oli.HometownPolitician.domain.bill.repository;

import com.oli.HometownPolitician.domain.bill.entity.PopularityBill;
import com.oli.HometownPolitician.domain.search.input.SearchInput;
import com.oli.HometownPolitician.global.factory.OrderSpecifierFactory;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oli.HometownPolitician.domain.bill.entity.QBill.bill;
import static com.oli.HometownPolitician.domain.bill.entity.QPopularityBill.popularityBill;
import static com.oli.HometownPolitician.domain.committee.entity.QCommittee.committee;

public class PopularityBillRepositoryImpl implements PopularityBillRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final PopularityBillRepositoryCond popularityBillCond;
    private final BillRepositoryCond billCond;

    public PopularityBillRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
        this.popularityBillCond = new PopularityBillRepositoryCond();
        this.billCond = new BillRepositoryCond();
    }

    @Override
    public List<PopularityBill> queryPopularityBillsBySearchInput(SearchInput input) {
        return queryFactory
                .selectFrom(popularityBill)
                .join(popularityBill.bill, bill).fetchJoin()
                .leftJoin(bill.committee, committee).fetchJoin()
                .where(
                        popularityBillCond.notDeleted()
                                .and(popularityBillCond.popularityBillDirection(input.getPagination()))
                                .and(popularityBillCond.popularityBillDirection(input.getPagination()))
                )
                .orderBy(
                        OrderSpecifierFactory.from(input.getPagination(), new PathBuilder(PopularityBill.class, "popularityBill"), "id")
                )
                .limit(popularityBillCond.popularityBillLimit(input.getPagination()))
                .fetch();
    }
}
