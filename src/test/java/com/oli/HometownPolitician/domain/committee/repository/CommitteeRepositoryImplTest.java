package com.oli.HometownPolitician.domain.committee.repository;

import com.oli.HometownPolitician.domain.committee.entity.Committee;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CommitteeRepositoryImplTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private CommitteeRepository committeeRepository;

    @BeforeEach
    void setUp() {
        insertCommitteeData();
        em.flush();
        em.clear();
    }

    @AfterEach
    void clear() {
        committeeRepository.deleteAll();
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("qFindAll이 잘 동작하는지 확인")
    void qFindAll_well_test() {
        List<Committee> committees = committeeRepository.qFindAll();

        assertThat(committees).isNotNull();
        assertThat(committees.size()).isEqualTo(16);
    }

    private List<Committee> insertCommitteeData() {
        List<String> committeeNameList = new ArrayList<>();
        committeeNameList.add("국회운영위원회");
        committeeNameList.add("법제사법위원회");
        committeeNameList.add("기획재정위원회");
        committeeNameList.add("교육위원회");
        committeeNameList.add("과학기술정보방송통신위원회");
        committeeNameList.add("외교통일위원회");
        committeeNameList.add("국방위원회");
        committeeNameList.add("행정안전위원회");
        committeeNameList.add("문화체육관광위원회");
        committeeNameList.add("국토교통위원회");
        committeeNameList.add("농림축산식품해양수산위원회");
        committeeNameList.add("산업통상자원중소벤처기업위원회");
        committeeNameList.add("보건복지위원회");
        committeeNameList.add("환경노동위원회");
        committeeNameList.add("정보위원회");
        committeeNameList.add("여성가족위원회");
        List<Committee> committeeList = new ArrayList<>();
        for (Long i = 0L; i < committeeNameList.size(); i++) {
            committeeList.add(Committee.builder()
                    .id(i + 1)
                    .name(committeeNameList.get(i.intValue()))
                    .external_committee_id((12345L + i.toString()))
                    .build());
        }
        committeeRepository.saveAll(committeeList);

        return committeeList;
    }
}