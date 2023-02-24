package com.oli.HometownPolitician.domain.bill.repository;

import com.oli.HometownPolitician.domain.billMessage.input.BillMessageRoomFilterInput;
import com.oli.HometownPolitician.domain.tag.dto.TagInput;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

import static com.oli.HometownPolitician.domain.bill.entity.QBill.bill;

public class BillRepositoryCond {
    public BooleanExpression billEqId(Long id) {
        if (id == null)
            return null;
        return bill.id.eq(id);
    }

    public BooleanExpression billNotDeleted() {
        return bill.deletedAt.isNull();
    }

    public BooleanExpression filter(BillMessageRoomFilterInput filterInput) {
        if (filterInput == null
                || filterInput.getTagList() == null
                || filterInput.getTagList().isEmpty())
            return null;
        List<Long> tagIds = filterInput.getTagList().stream().map(TagInput::getId).toList();
        return billTagsContaionsOneOfTagIdList(tagIds);
    }

    public BooleanExpression billTagsContaionsOneOfTagIdList(List<Long> tagIdList) {
        if (tagIdList.size() == 0)
            return null;
        return bill.tags.any().tag.id.in(tagIdList);
    }
}
