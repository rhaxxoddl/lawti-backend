package com.oli.HometownPolitician.domain.bill.repository;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import static com.oli.HometownPolitician.domain.bill.entity.QBill.bill;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class BillRepositoryImpl implements BillRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public BillRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Bill> queryBillsByIdList(List<Long> idList) {
        return queryFactory.selectFrom(bill)
                .where(bill.id.in(idList))
                .fetch();
    }
}
