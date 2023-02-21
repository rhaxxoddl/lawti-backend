package com.oli.HometownPolitician.domain.billMessage.repository;

import com.oli.HometownPolitician.domain.billMessage.entity.BillMessage;
import com.oli.HometownPolitician.domain.billMessage.input.BillMessageRoomListInput;
import com.oli.HometownPolitician.domain.billUserRelation.entity.BillUserRelation;

import java.util.List;

public interface BillMessageRepositoryCustom {
    List<BillUserRelation> qFindByUserIdAndFilter(BillMessageRoomListInput input, Long userId);
    List<BillMessage> qFindByBillId(Long billId);
}
