package com.oli.HometownPolitician.domain.bill.repository;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.committee.repository.CommitteeRepositoryCond;
import com.oli.HometownPolitician.domain.search.input.SearchInput;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oli.HometownPolitician.domain.bill.entity.QBill.bill;
import static com.oli.HometownPolitician.domain.billTagRelation.entity.QBillTagRelation.billTagRelation;
import static com.oli.HometownPolitician.domain.committee.entity.QCommittee.committee;
import static com.oli.HometownPolitician.domain.politician.entity.QPolitician.politician;
import static com.oli.HometownPolitician.domain.proposer.entity.QProposer.proposer;
import static com.oli.HometownPolitician.domain.tag.entity.QTag.tag;

public class BillRepositoryImpl implements BillRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final BillRepositoryCond billCond;
    private final CommitteeRepositoryCond committeeCond;

    public BillRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.billCond = new BillRepositoryCond();
        this.committeeCond = new CommitteeRepositoryCond();
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
                .selectDistinct(bill)
                .from(bill)
                .leftJoin(bill.committee, committee).fetchJoin()
                .leftJoin(bill.billTagRelations, billTagRelation).leftJoin(billTagRelation.tag, tag)
                .leftJoin(bill.proposers, proposer).leftJoin(proposer.politician, politician)
                .where(
                        billCond.billNotDeleted()
                                .and(billCond.searchFilter(input.getFilter()))
                                .and(billCond.searchBillDirection(input))
                                .and(billCond.getMatchedKeyword(input.getKeyword()))

                )
                .orderBy(
                        billCond.searchOrderByList(input).stream().toArray(OrderSpecifier[]::new)
                )
                .limit(billCond.billLimit(input.getPagination()))
                .fetch();
    }
}
