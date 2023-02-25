package com.oli.HometownPolitician.domain.bill.repository;

import com.oli.HometownPolitician.domain.billMessage.input.BillMessageRoomFilterInput;
import com.oli.HometownPolitician.domain.committee.repository.CommitteeRepositoryCond;
import com.oli.HometownPolitician.domain.search.input.SearchFilterInput;
import com.oli.HometownPolitician.domain.tag.dto.TagInput;
import com.oli.HometownPolitician.domain.tag.repository.TagRepositoryCond;
import com.oli.HometownPolitician.global.argument.input.TargetSlicePaginationInput;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

import static com.oli.HometownPolitician.domain.bill.entity.QBill.bill;

public class BillRepositoryCond {
    private final CommitteeRepositoryCond committeeCond;
    private final TagRepositoryCond tagCond;

    public BillRepositoryCond() {
        this.committeeCond = new CommitteeRepositoryCond();
        this.tagCond = new TagRepositoryCond();
    }

    public BooleanExpression billEqId(Long id) {
        if (id == null)
            return null;
        return bill.id.eq(id);
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
        return billTagsContaionsOneOfTagIdList(tagIds);
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


    public Long billLimit(TargetSlicePaginationInput pagination) {
        if (pagination == null) {
            return null;
        }
        return Long.valueOf(pagination.getElementSize());
    }

    public BooleanExpression billTagsContaionsOneOfTagIdList(List<Long> tagIdList) {
        if (tagIdList.size() == 0)
            return null;
        return bill.tags.any().tag.id.in(tagIdList);
    }

    public BooleanBuilder searchFilter(SearchFilterInput input) {
        BooleanBuilder builder = new BooleanBuilder();
        if (input == null)
            return null;
        return builder
                .and(committeeCond.eqCommitteeInput(input.getCommittee()))
                .and(tagCond.tagEqTagInput(input.getTag()));
    }
}
