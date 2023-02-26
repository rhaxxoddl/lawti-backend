package com.oli.HometownPolitician.domain.bill.repository;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.search.input.SearchInput;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oli.HometownPolitician.domain.bill.entity.QBill.bill;
import static com.oli.HometownPolitician.domain.billUserRelation.entity.QBillUserRelation.billUserRelation;
import static com.oli.HometownPolitician.domain.committee.entity.QCommittee.committee;

public class BillRepositoryImpl implements BillRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final BillRepositoryCond billCond;

    public BillRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.billCond = new BillRepositoryCond();
    }

    @Override
    public List<Bill> queryBillsByIdList(List<Long> idList) {
        return queryFactory
                .selectFrom(bill)
                .where(bill.id.in(idList))
                .fetch();
    }

    @Override
    public List<Bill> queryBillsBySearchInput(SearchInput input) {
        return queryFactory
                .selectFrom(bill)
                .leftJoin(bill.committee, committee)
                .leftJoin(bill.followedBillUserRelations, billUserRelation)
                .where(
                        billCond.billNotDeleted()
                                .and(billCond.searchFilter(input.getFilter()))
                                .and(billCond.searchBillDirection(input))
                                .and(billCond.getMatchedKeyword(input.getSearchText()))

                )
                .orderBy(
                        billCond.searchOrderByList(input).stream().toArray(OrderSpecifier[]::new)
                )
                .limit(billCond.billLimit(input.getPagination()))
                .fetch();
    }

    private static OrderSpecifier<?> numberOfFollower(SearchInput input) {
        if (input == null || input.getFilter() == null)
            return null;
        return (OrderSpecifier<?>) JPAExpressions
                .select(billUserRelation.count())
                .from(billUserRelation)
                .where(
                        billUserRelation.bill.id.eq(bill.id)
                                .and(billUserRelation.isUnfollowed.isFalse())
                );
    }
}
