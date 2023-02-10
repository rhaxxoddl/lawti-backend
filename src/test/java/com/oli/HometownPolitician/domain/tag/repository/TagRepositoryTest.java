package com.oli.HometownPolitician.domain.tag.repository;

import com.oli.HometownPolitician.domain.tag.entity.Tag;
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
class TagRepositoryTest {
    static private final int INTERESTS_SIZE = 9;
    static private final String INTEREST1 = "국회, 인권";
    static private final String INTEREST2 = "형법, 민법, 범죄";
    static private final String INTEREST3 = "금융, 공정거래, 보훈(국가유공)";
    static private final String INTEREST4 = "관세, 세금, 통계";
    static private final String INTEREST5 = "교육, 교육공무원";
    static private final String INTEREST6 = "과학, 방송, 통신, 인터넷";
    static private final String INTEREST7 = "외교, 통일, 북한이탈주민, 재외동포, 해외";
    static private final String INTEREST8 = "병역, 국방";
    static private final String INTEREST9 = "경찰, 소방, 선거, 공무원, 재난, 운전";

    @Autowired
    private TagRepository tagRepository;


    @BeforeEach
    private void initialData() {

        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag(INTEREST1));
        tagList.add(new Tag(INTEREST2));
        tagList.add(new Tag(INTEREST3));
        tagList.add(new Tag(INTEREST4));
        tagList.add(new Tag(INTEREST5));
        tagList.add(new Tag(INTEREST6));
        tagList.add(new Tag(INTEREST7));
        tagList.add(new Tag(INTEREST8));
        tagList.add(new Tag(INTEREST9));
        tagRepository.saveAll(tagList);
    }

    @Test
    @DisplayName("저장된 관심분야 전체가 잘 나오는지 확인")
    public void tags_all_well_test() {
        List<Tag> tagList = tagRepository.findAll();
        assertThat(tagList.size()).isEqualTo(INTERESTS_SIZE);
        assertThat(tagList.get(0).getName()).isEqualTo(INTEREST1);
        assertThat(tagList.get(1).getName()).isEqualTo(INTEREST2);
        assertThat(tagList.get(2).getName()).isEqualTo(INTEREST3);
        assertThat(tagList.get(3).getName()).isEqualTo(INTEREST4);
        assertThat(tagList.get(4).getName()).isEqualTo(INTEREST5);
        assertThat(tagList.get(5).getName()).isEqualTo(INTEREST6);
        assertThat(tagList.get(6).getName()).isEqualTo(INTEREST7);
        assertThat(tagList.get(7).getName()).isEqualTo(INTEREST8);
        assertThat(tagList.get(8).getName()).isEqualTo(INTEREST9);
    }
}