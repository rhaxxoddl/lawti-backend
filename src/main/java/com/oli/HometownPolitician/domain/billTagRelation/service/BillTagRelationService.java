package com.oli.HometownPolitician.domain.billTagRelation.service;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.billTagRelation.entity.BillTagRelation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class BillTagRelationService {
    private final BillTagRelationProvider billTagRelationProvider;

    public BillTagRelation createeBillTagRelationByCommittee(Bill bill) {
        return BillTagRelation.from(
                bill,
                billTagRelationProvider.matchTagByCommittee(bill.getCommittee())
        );
    }
}
