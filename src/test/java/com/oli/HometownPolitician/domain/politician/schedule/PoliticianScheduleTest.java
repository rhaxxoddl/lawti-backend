package com.oli.HometownPolitician.domain.politician.schedule;

import com.oli.HometownPolitician.domain.politician.entity.Politician;
import com.oli.HometownPolitician.domain.politician.repository.PoliticianRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PoliticianScheduleTest {
    @Autowired
    private PoliticianSchedule politicianSchedule;
    @Autowired
    private PoliticianRepository politicianRepository;

    @Test
    @DisplayName("parseCurrentStatusOfPoliticians이 잘 작동하는지 확인")
    void parseCurrentStatusOfPoliticians_well_test() {
        List<Politician> beforePoliticians = politicianRepository.findAll();
        assertThat(beforePoliticians.size()).isEqualTo(0);
        politicianSchedule.parseCurrentStatusOfPoliticians();
        List<Politician> afterPoliticians = politicianRepository.findAll();
        assertThat(afterPoliticians.size()).isGreaterThan(300);
        assertThat(afterPoliticians.size()).isEqualTo(314);
    }
}