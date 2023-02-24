package com.oli.HometownPolitician.domain.billMessage.repository;

import static com.oli.HometownPolitician.domain.bill.entity.QBill.bill;
import com.oli.HometownPolitician.domain.bill.repository.BillRepositoryCond;
import com.oli.HometownPolitician.domain.billMessage.entity.BillMessage;

import static com.oli.HometownPolitician.domain.billMessage.entity.QBillMessage.billMessage;

import com.oli.HometownPolitician.domain.billMessage.input.BillMessageListInput;
import com.oli.HometownPolitician.global.factory.OrderSpecifierFactory;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class BillMessageRepositoryImpl implements BillMessageRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final BillMessageRepositoryCond billMessageCond;
    private final BillRepositoryCond billCond;

    public BillMessageRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.billMessageCond = new BillMessageRepositoryCond();
        this.billCond = new BillRepositoryCond();
    }

    @Override
    public List<BillMessage> qFindByBillId(BillMessageListInput input) {
        return queryFactory.selectFrom(billMessage)
                .join(billMessage.bill, bill).fetchJoin()
                .where(
                        billMessageCond.notDeleted()
                                .and(billCond.billEqId(input.getBillId()))
                                .and(billCond.billNotDeleted())
                                .and(billMessageCond.billMessageDirection(input.getPagination()))
                )
                .orderBy(
                        OrderSpecifierFactory.from(input.getPagination(), new PathBuilder(BillMessage.class, "billMessage"), "createdAt"),
                        OrderSpecifierFactory.from(input.getPagination(), new PathBuilder(BillMessage.class, "billMessage"), "id")
                )
                .limit(billMessageCond.billMessageLimit(input.getPagination()))
                .fetch();
    }
}
