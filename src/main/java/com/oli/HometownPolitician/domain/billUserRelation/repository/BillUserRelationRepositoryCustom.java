package com.oli.HometownPolitician.domain.billUserRelation.repository;

import com.oli.HometownPolitician.domain.billMessage.input.BillMessageRoomListInput;
import com.oli.HometownPolitician.domain.billUserRelation.entity.BillUserRelation;

import java.util.List;

public interface BillUserRelationRepositoryCustom {
    List<BillUserRelation> qFindByUserIdAndFilter(BillMessageRoomListInput input, Long userId);
}
