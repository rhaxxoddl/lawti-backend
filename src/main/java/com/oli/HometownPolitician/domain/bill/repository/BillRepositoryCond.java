package com.oli.HometownPolitician.domain.bill.repository;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.bill.entity.QBill;
import com.oli.HometownPolitician.domain.billMessage.input.BillMessageRoomFilterInput;
import com.oli.HometownPolitician.domain.billUserRelation.repository.BillUserRelationRepositoryCond;
import com.oli.HometownPolitician.domain.committee.repository.CommitteeRepositoryCond;
import com.oli.HometownPolitician.domain.search.enumeration.SearchResultOrderBy;
import com.oli.HometownPolitician.domain.search.input.SearchFilterInput;
import com.oli.HometownPolitician.domain.search.input.SearchInput;
import com.oli.HometownPolitician.domain.tag.dto.TagInput;
import com.oli.HometownPolitician.domain.tag.repository.TagRepositoryCond;
import com.oli.HometownPolitician.global.argument.input.TargetSlicePaginationInput;
import com.oli.HometownPolitician.global.factory.OrderSpecifierFactory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.oli.HometownPolitician.domain.bill.entity.QBill.bill;

public class BillRepositoryCond {
    private final CommitteeRepositoryCond committeeCond;
    private final BillUserRelationRepositoryCond billUserRelationCond;
    private final TagRepositoryCond tagCond;

    public BillRepositoryCond() {
        this.committeeCond = new CommitteeRepositoryCond();
        this.billUserRelationCond = new BillUserRelationRepositoryCond();
        this.tagCond = new TagRepositoryCond();
    }

    public BooleanExpression billEqId(Long id) {
        if (id == null)
            return null;
        return bill.id.eq(id);
    }

    public BooleanExpression targetBillEqId(Long id) {
        QBill targetBill = new QBill("targetBill");
        if (id == null)
            return null;
        return targetBill.id.eq(id);
    }

    public BooleanExpression billNotDeleted() {
        return bill.deletedAt.isNull();
    }

    public BooleanExpression billMessageRoomFilter(BillMessageRoomFilterInput filterInput) {
        if (filterInput == null
                || filterInput.getTagList() == null
                || filterInput.getTagList().isEmpty())
            return null;
        List<Long> tagIds = filterInput.getTagList().stream().map(TagInput::getId).toList();
        return tagsInTagIdList(tagIds);
    }

    public BooleanBuilder searchBillDirection(SearchInput input) {
        if (input.getOrderBy() == null || input.getOrderBy() == SearchResultOrderBy.RECENTLY)
            return billDirection(input.getPagination());
        return popularityBillDirection(input.getPagination());
    }

    public BooleanBuilder billDirection(TargetSlicePaginationInput pagination) {
        BooleanBuilder builder = new BooleanBuilder();
        if (pagination == null || pagination.getTarget() == null) {
            return builder.and(null);
        } else if (pagination.getIsAscending()) {
            return builder.and(bill.id.gt(pagination.getTarget()));
        } else {
            return builder.and(bill.id.lt(pagination.getTarget()));
        }
    }

    public BooleanBuilder popularityBillDirection(TargetSlicePaginationInput pagination) {
        BooleanBuilder builder = new BooleanBuilder();
        if (pagination == null || pagination.getTarget() == null) {
            return builder.and(null);
        } else if (pagination.getIsAscending()) {
            return builder.and(bill.followerCount
                            .goe(getTargetBillFollowerCount(pagination.getTarget())))
                    .and(bill.followerCount.gt(getTargetBillFollowerCount(pagination.getTarget()))
                            .or(bill.updatedAt.after(getTargetBillUpdatedAt(pagination.getTarget()))
                                    .or(bill.updatedAt.eq(getTargetBillUpdatedAt(pagination.getTarget())))))
                    .and(bill.followerCount.gt(getTargetBillFollowerCount(pagination.getTarget()))
                            .or(bill.updatedAt.after(getTargetBillUpdatedAt(pagination.getTarget())))
                            .or(bill.id.gt(pagination.getTarget())));
        } else {
            return builder.and(bill.followerCount
                            .loe(getTargetBillFollowerCount(pagination.getTarget())))
                    .and(bill.followerCount.lt(getTargetBillFollowerCount(pagination.getTarget()))
                            .or(bill.updatedAt.before(getTargetBillUpdatedAt(pagination.getTarget()))
                                    .or(bill.updatedAt.eq(getTargetBillUpdatedAt(pagination.getTarget())))))
                    .and(bill.followerCount.lt(getTargetBillFollowerCount(pagination.getTarget()))
                            .or(bill.updatedAt.before(getTargetBillUpdatedAt(pagination.getTarget())))
                            .or(bill.id.lt(pagination.getTarget())));
        }
    }

    public JPQLQuery<Long> getTargetBillFollowerCount(Long target) {
        QBill targetBill = new QBill("targetBill");
        return JPAExpressions.select(targetBill.followerCount)
                .from(targetBill)
                .where(targetBill.id.eq(target));
    }

    public JPQLQuery<LocalDateTime> getTargetBillUpdatedAt(Long target) {
        QBill targetBill = new QBill("targetBill");
        return JPAExpressions.select(targetBill.updatedAt)
                .from(targetBill)
                .where(targetBill.id.eq(target));
    }

    public BooleanBuilder getMatchedKeyword(String keyword) {
        BooleanBuilder builder = new BooleanBuilder();
        if (keyword == null || keyword.isEmpty())
            return null;
        return builder.and(
                bill.title.contains(keyword)
                        .or(bill.proposers.any().politician.name.contains(keyword))
                        .or(bill.committee.name.contains(keyword))
        );
    }


    public Long billLimit(TargetSlicePaginationInput pagination) {
        if (pagination == null) {
            return null;
        }
        return Long.valueOf(pagination.getElementSize());
    }

    public BooleanExpression tagsInTagIdList(List<Long> tagIdList) {
        if (tagIdList.size() == 0)
            return null;
        return bill.billTagRelations.any().tag.id.in(tagIdList);
    }

    public BooleanBuilder searchFilter(SearchFilterInput input) {
        BooleanBuilder builder = new BooleanBuilder();
        if (input == null)
            return null;
        if (input.getCommittee() != null)
            builder.and(bill.committee.id.eq(input.getCommittee().getCommitteeId()));
        if (input.getTag() != null)
            builder.and(bill.billTagRelations.any().tag.id.eq(input.getTag().getId()));
        return builder;
    }

    public List<OrderSpecifier> searchOrderByList(SearchInput input) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if (input.getOrderBy() != null && input.getOrderBy() == SearchResultOrderBy.POPULARITY)
            orderSpecifiers.add(OrderSpecifierFactory.from(input.getPagination(), new PathBuilder(Bill.class, "bill"), "followerCount"));

        orderSpecifiers.add(OrderSpecifierFactory.from(input.getPagination(), new PathBuilder(Bill.class, "bill"), "updatedAt"));
        orderSpecifiers.add(OrderSpecifierFactory.from(input.getPagination(), new PathBuilder(Bill.class, "bill"), "id"));
        return orderSpecifiers;
    }
}
