package com.oli.HometownPolitician.domain.bill.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class PopularityBillService {
    public void updatePopularityBills() {
//        TODO 인기순 법안 테이블 업데이트 메서드 구현
    }
}
