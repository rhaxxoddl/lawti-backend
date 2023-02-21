package com.oli.HometownPolitician.domain.billMessage.repository;

import com.oli.HometownPolitician.domain.billMessage.entity.BillMessage;

import java.util.List;

public interface BillMessageRepositoryCustom {
    List<BillMessage> qFindByBillId(Long billId);
}
