package com.oli.HometownPolitician.domain.billMessage.service;

import com.oli.HometownPolitician.domain.bill.enumeration.BillStageType;
import com.oli.HometownPolitician.domain.billMessage.repository.BillMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.management.openmbean.InvalidKeyException;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class BillMessageGenerator {
    private final BillMessageRepository billMessageRepository;

//    public List<BillMessage> generateBillMessagesUntilNow(Bill bill) {
//        List<BillMessage> billMessageList = new ArrayList<>();
//        billMessageList.add()
//    }

    private String getMessageByBillStage(BillStageType stageType) {
        switch (stageType) {
            case RECEIPT:
                return "이(가) 국회에 접수되었어요";
            case COMMITTEE_RECEIPT:
                return "이(가) 상임위원회에 접수되었어요";
            case COMMITTEE_REVIEW:
                return "이(가) 상임위원회에서 심사중이에요";
            case SYSTEMATIC_REVIEW:
                return "이(가) 법제사법위원회에서 체계·자구 심사중이에요";
            case MAIN_SESSION_AGENDA:
                return "이(가) 국회 본회의에 부의되었어요";
            case MAIN_SESSION_REJECTION:
                return "이(가) 국회 본회의에 불부의되었어요";
            case PROMULGATION:
                return "이(가) 공포되었어요";
            case ALTERNATIVE_DISCARD:
                return "이(가) 대안이 반영되어 폐기되었어요";
            case DISCARD:
                return "이(가) 폐기되었어요";
            case WITHDRAWAL:
                return "이(가) 철수되었어요";
            default:
                throw new InvalidKeyException("정의되지 않은 타입입니다");
        }
    }
}
