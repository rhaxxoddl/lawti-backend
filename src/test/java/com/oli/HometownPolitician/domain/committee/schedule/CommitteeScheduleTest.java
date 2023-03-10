package com.oli.HometownPolitician.domain.committee.schedule;

import com.oli.HometownPolitician.domain.committee.entity.Committee;
import com.oli.HometownPolitician.domain.committee.repository.CommitteeRepository;
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
class CommitteeScheduleTest {
    @Autowired
    private CommitteeSchedule committeeSchedule;
    @Autowired
    private CommitteeRepository committeeRepository;

    @Test
    @DisplayName("위원회 현황을 잘 불러와서 저장하는지 확인")
    void parseCurrentStatusOfCommittees() {
        List<Committee> beforeCommittees = committeeRepository.qFindAll();
        Assertions.assertThat(beforeCommittees).isNotNull();
        Assertions.assertThat(beforeCommittees.size()).isEqualTo(0);

        committeeSchedule.parseCurrentStatusOfCommittees();

        List<Committee> afterCommittees = committeeRepository.qFindAll();
        Assertions.assertThat(afterCommittees).isNotNull();
        Assertions.assertThat(afterCommittees.size()).isGreaterThan(10);
    }
}