package com.oli.HometownPolitician.domain.bill.schedule;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.bill.repository.BillRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BillScheduleTest {
    @Autowired
    private BillSchedule billSchedule;
    @Autowired
    private BillRepository billRepository;

    @Test
    @DisplayName("의안 목록을 잘 불러와서 저장하는지 확인")
    void parseGetBillInfoList_well_test() {
        List<Bill> beforeBills = billRepository.findAll();
        Assertions.assertThat(beforeBills).isNotNull();
        Assertions.assertThat(beforeBills.size()).isEqualTo(0);

        billSchedule.parseGetBillInfoList1();
        billSchedule.parseGetBillInfoList2();
        billSchedule.parseGetBillInfoList3();

        List<Bill> afterBills = billRepository.findAll();
        Assertions.assertThat(afterBills).isNotNull();
        Assertions.assertThat(afterBills.size()).isGreaterThan(19000);

    }
}