package com.oli.HometownPolitician.domain.interest.service;

import com.oli.HometownPolitician.domain.interest.dto.InterestsDto;
import com.oli.HometownPolitician.domain.interest.entity.Interest;
import com.oli.HometownPolitician.domain.interest.repository.InterestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class InterestServiceTest {
    static private final int INTERESTS_SIZE = 9;
    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private InterestService interestService;

    @BeforeEach
    private void initialData() {
        List<Interest> interestList = new ArrayList<>();
        interestList.add(new Interest(1L, "국회, 인권"));
        interestList.add(new Interest(2L, "형법, 민법, 범죄"));
        interestList.add(new Interest(3L, "금융, 공정거래, 보훈(국가유공)"));
        interestList.add(new Interest(4L, "관세, 세금, 통계"));
        interestList.add(new Interest(5L, "교육, 교육공무원"));
        interestList.add(new Interest(6L, "과학, 방송, 통신, 인터넷"));
        interestList.add(new Interest(7L, "외교, 통일, 북한이탈주민, 재외동포, 해외"));
        interestList.add(new Interest(8L, "병역, 국방"));
        interestList.add(new Interest(9L, "경찰, 소방, 선거, 공무원, 재난, 운전"));
        interestRepository.saveAll(interestList);
    }

    @Test
    @DisplayName("queryInterest에서 InterestsDto를 잘 반환하는지 확인")
    public void queryinterests_well_test() {
        InterestsDto interestsDto = interestService.queryInterests();
        assertThat(interestsDto).isNotNull();
        assertThat(interestsDto.getList()).isNotNull();
        assertThat(interestsDto.getList().size()).isEqualTo(INTERESTS_SIZE);
    }

}