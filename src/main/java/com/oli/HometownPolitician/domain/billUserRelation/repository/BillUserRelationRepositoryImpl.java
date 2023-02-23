package com.oli.HometownPolitician.domain.billUserRelation.repository;

import static com.oli.HometownPolitician.domain.bill.entity.QBill.bill;

import com.oli.HometownPolitician.domain.bill.repository.BillRepositoryCond;
import static com.oli.HometownPolitician.domain.billMessage.entity.QBillMessage.billMessage;

import com.oli.HometownPolitician.domain.billMessage.entity.BillMessage;
import com.oli.HometownPolitician.domain.billMessage.input.BillMessageRoomListInput;
import com.oli.HometownPolitician.domain.billUserRelation.entity.BillUserRelation;

import static com.oli.HometownPolitician.domain.billUserRelation.entity.QBillUserRelation.billUserRelation;

import com.oli.HometownPolitician.domain.tag.dto.TagInput;
import static com.oli.HometownPolitician.domain.user.entity.QUser.user;

import com.oli.HometownPolitician.domain.user.repository.UserRepositoryCond;
import com.oli.HometownPolitician.global.factory.OrderSpecifierFactory;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class BillUserRelationRepositoryImpl implements BillUserRelationRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final BillRepositoryCond billCond;
    private final BillUserRelationCond billUserRelationCond;
    private final UserRepositoryCond userCond;

    public BillUserRelationRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.billCond = new BillRepositoryCond();
        this.billUserRelationCond = new BillUserRelationCond();
        this.userCond = new UserRepositoryCond();
    }

    @Override
    public List<BillUserRelation> qFindByUserUuidAndFilter(BillMessageRoomListInput input, String userUuid) {
        return queryFactory.selectFrom(billUserRelation)
                .join(billUserRelation.bill, bill)
                .join(billUserRelation.user, user).fetchJoin()
                .join(bill.billMessages, billMessage).on(billMessage.createdAt.max().eq(billMessage.createdAt))
                .where(
                        billUserRelationCond.notDeleted()
                                .and(billUserRelationCond.notUnfollowed())
                                .and(userCond.userEqUuid(userUuid))
                                .and(userCond.userNotDeleted())
                                .and(billCond.billTagsContaionsOneOfTagIdList(
                                        input.getFilter().getTagList().stream().map(TagInput::getId).toList()
                                ))
                )
                .orderBy(
                        OrderSpecifierFactory.from(input.getPagination(), new PathBuilder(BillMessage.class, "billMessage"), "createdAt"),
                        OrderSpecifierFactory.from(input.getPagination(), new PathBuilder(BillMessage.class, "billMessage"), "id")
                )
                .limit(billUserRelationCond.billUserRelationLimit(input.getPagination()))
                .fetch();
    }
}
