package com.oli.HometownPolitician.domain.bill.service;

import com.oli.HometownPolitician.domain.bill.dto.BillDetailDto;
import com.oli.HometownPolitician.domain.bill.dto.BillPdfUriDto;
import com.oli.HometownPolitician.domain.bill.dto.FollowingBillsDto;
import com.oli.HometownPolitician.domain.bill.input.BillInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class BillService {

    public BillDetailDto queryBillDetail(BillInput billInput) {
        return null;
    }
    public BillPdfUriDto queryBillPdfUri(BillInput billInput) {
        return null;
    }public FollowingBillsDto followBill(BillInput billInput, String authorization) {
        return null;
    }public FollowingBillsDto unfollowBill(BillInput billInput, String authorization) {
        return null;
    }
}
