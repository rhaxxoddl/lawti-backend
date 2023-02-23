package com.oli.HometownPolitician.domain.billMessage.repository;

import com.oli.HometownPolitician.domain.billMessage.entity.BillMessage;
import com.oli.HometownPolitician.domain.billMessage.input.BillMessageListInput;

import java.util.List;

public interface BillMessageRepositoryCustom {
    List<BillMessage> qFindByBillId(BillMessageListInput billMessageListInput);
}
