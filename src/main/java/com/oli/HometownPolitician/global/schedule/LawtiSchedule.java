package com.oli.HometownPolitician.global.schedule;

import com.oli.HometownPolitician.domain.bill.schedule.BillSchedule;
import com.oli.HometownPolitician.domain.committee.schedule.CommitteeSchedule;
import com.oli.HometownPolitician.domain.politician.schedule.PoliticianSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class LawtiSchedule {
    private final CommitteeSchedule committeeSchedule;
    private final PoliticianSchedule politicianSchedule;
    private final BillSchedule billSchedule;
    private final int COMMITTEE_CYCLE_MILLISECONDS = 1000 * 60 * 60 * 24;
    private final int POLITICIAN_CYCLE_MILLISECONDS = 1000 * 60 * 60 * 24;
    private final int BILL_CYCLE_MILLISECONDS = 1000 * 60 * 60 * 8;

    @PostConstruct
    public void initLawtiDatabase() {
        initCommittee();
        initPolitician();
        initBill();
    }

    public void initCommittee() {
        committeeSchedule.parseCurrentStatusOfCommittees();
    }

    public void initPolitician() {
        politicianSchedule.parseCurrentStatusOfPoliticians();
    }

    public void initBill() {
        billSchedule.parseGetBillInfoList(1, 0);
        billSchedule.parseSearchBills(250, 0);
    }
}
