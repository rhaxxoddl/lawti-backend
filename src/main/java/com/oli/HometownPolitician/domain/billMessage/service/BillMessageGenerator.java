package com.oli.HometownPolitician.domain.billMessage.service;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.bill.enumeration.BillStageType;
import com.oli.HometownPolitician.domain.billMessage.entity.BillMessage;

public class BillMessageGenerator {
    public BillMessage generateBillMessageByCurrentStage(Bill bill, BillStageType currentStage) {
        return BillMessage.builder()
                .bill(bill)
                .content(bill.getTitle() + getMessageByBillStage(currentStage))
                .build();
    }

    private String getMessageByBillStage(BillStageType stageType) {
        return switch (stageType) {
            case RECEIPT -> "이(가) 국회에 접수되었어요";
            case COMMITTEE_RECEIPT -> "이(가) 상임위원회에 접수되었어요";
            case COMMITTEE_REVIEW -> "이(가) 상임위원회에서 심사중이에요";
            case SYSTEMATIC_REVIEW -> "이(가) 법제사법위원회에서 체계·자구 심사중이에요";
            case MAIN_SESSION_AGENDA -> "이(가) 국회 본회의에 부의되었어요";
            case PROMULGATION -> "이(가) 공포되었어요";
            case MAIN_SESSION_REJECTION -> "이(가) 국회 본회의에 불부의되었어요";
            case ALTERNATIVE_DISCARD -> "이(가) 대안이 반영되어 폐기되었어요";
            case DISCARD -> "이(가) 폐기되었어요";
            case WITHDRAWAL -> "이(가) 철수되었어요";
        };
    }
}
