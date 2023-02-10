package com.oli.HometownPolitician.domain.tag.service;

import com.oli.HometownPolitician.domain.tag.dto.TagsDto;
import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.tag.repository.TagRepository;
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
class TagServiceTest {
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

    @Autowired
    private TagService tagService;

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
    @DisplayName("queryInterest에서 InterestsDto를 잘 반환하는지 확인")
    public void queryinterests_well_test() {
        TagsDto tagsDto = tagService.queryTags();
        assertThat(tagsDto).isNotNull();
        assertThat(tagsDto.getList()).isNotNull();
        assertThat(tagsDto.getList().size()).isEqualTo(INTERESTS_SIZE);
    }

}