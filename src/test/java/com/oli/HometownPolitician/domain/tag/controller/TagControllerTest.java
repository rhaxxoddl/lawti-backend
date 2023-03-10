package com.oli.HometownPolitician.domain.tag.controller;

import com.oli.HometownPolitician.domain.tag.dto.TagDto;
import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.tag.repository.TagRepository;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.oli.HometownPolitician.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureGraphQlTester
@Transactional
class TagControllerTest {
    private final String USER_UUID = "asfasd-asdfasd-fasdf-qwe";
    @Autowired
    private GraphQlTester graphQlTester;

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        insertTagData();
        insertUserData();
    }

    @Test
    @DisplayName("관심분야 리스트 반환됨을 확인")
    public void tags_well_test() {
        graphQlTester.documentName("tag")
                .operationName("queryTags")
                .execute()
                .errors()
                .verify()
                .path("queryTags.list")
                .entityList(TagDto.class)
                .path("queryTags.list[*].id")
                .entityList(Long.class)
                .path("queryTags.list[*].name")
                .entityList(String.class);
    }

    //    TODO 해당 테스트는 현재 제대로 동작하지 않음. 수정필요.
//          Authorization 헤더를 추가할 수 없어서 동작하지 않음.
    @Test
    @DisplayName("following 호출 잘되는지 확인")
    void followMyTags() {
        Map<String, Object> tag = new ConcurrentHashMap<>() {{
            put("id", 1);
            put("name", "국회, 인권");
        }};
        List<Map<String, Object>> tagInputList = new ArrayList<>();
        tagInputList.add(tag);
        Map<String, Object> input = new ConcurrentHashMap<>() {{
            put("list", tagInputList.toArray());
        }};
        graphQlTester.documentName("tag")
                .operationName("followMyTags")
                .variable("input", input)
                .execute()
                .errors()
                .verify()
                .path("followMyTags.list")
                .entityList(TagDto.class);
    }


    //    TODO 해당 테스트는 현재 제대로 동작하지 않음. 수정필요
    @Test
    @DisplayName("unfollowing 호출 잘되는지 확인")
    void unfollowMyTags() {
        Map<String, Object> tag = new ConcurrentHashMap<>() {{
            put("id", 1L);
            put("name", "국회, 인권");
        }};
        List<Map<String, Object>> tagInputList = new ArrayList<>();
        tagInputList.add(tag);
        Map<String, Object> input = new ConcurrentHashMap<>() {{
            put("list", tagInputList.toArray());
        }};
        graphQlTester.documentName("tag")
                .operationName("unfollowMyTags")
                .variable("input", input)
                .execute()
                .errors()
                .verify()
                .path("unfollowMyTags.list")
                .entityList(TagDto.class);
    }    private List<Tag> insertTagData() {
        final int INTERESTS_SIZE = 17;
        final String INTEREST1 = "국회, 인권";
        final String INTEREST2 = "형법, 민법, 범죄";
        final String INTEREST3 = "금융, 공정거래, 보훈(국가유공)";
        final String INTEREST4 = "관세, 세금, 통계";
        final String INTEREST5 = "교육, 교육공무원";
        final String INTEREST6 = "과학, 방송, 통신, 인터넷";
        final String INTEREST7 = "외교, 통일, 북한이탈주민, 재외동포, 해외";
        final String INTEREST8 = "병역, 국방";
        final String INTEREST9 = "경찰, 소방, 선거, 공무원, 재난, 운전";
        final String INTEREST10 = "문화재, 예술, 체육, 저작권, 게임";
        final String INTEREST11 = "부동산, 주택, 건설, 교통, 물류, 자동차";
        final String INTEREST12 = "농업, 임업, 축업,수산업, 산림, 식품";
        final String INTEREST13 = "에너지, 특허, 중소벤처, 창업, 소상공인";
        final String INTEREST14 = "의료, 보건, 건강보험, 복지";
        final String INTEREST15 = "환경, 고용, 노동";
        final String INTEREST16 = "테러, 기술보호(방산, 산업), 국가 보안";
        final String INTEREST17 = "아동, 청소년, 여성, 가족, 양성평등";


        List<Tag> tagList = new ArrayList<>();
        tagList.add(Tag.builder().name(INTEREST1).billTagRelations(new ArrayList<>()).userTagRelations(new ArrayList<>()).build());
        tagList.add(Tag.builder().name(INTEREST2).billTagRelations(new ArrayList<>()).userTagRelations(new ArrayList<>()).build());
        tagList.add(Tag.builder().name(INTEREST3).billTagRelations(new ArrayList<>()).userTagRelations(new ArrayList<>()).build());
        tagList.add(Tag.builder().name(INTEREST4).billTagRelations(new ArrayList<>()).userTagRelations(new ArrayList<>()).build());
        tagList.add(Tag.builder().name(INTEREST5).billTagRelations(new ArrayList<>()).userTagRelations(new ArrayList<>()).build());
        tagList.add(Tag.builder().name(INTEREST6).billTagRelations(new ArrayList<>()).userTagRelations(new ArrayList<>()).build());
        tagList.add(Tag.builder().name(INTEREST7).billTagRelations(new ArrayList<>()).userTagRelations(new ArrayList<>()).build());
        tagList.add(Tag.builder().name(INTEREST8).billTagRelations(new ArrayList<>()).userTagRelations(new ArrayList<>()).build());
        tagList.add(Tag.builder().name(INTEREST9).billTagRelations(new ArrayList<>()).userTagRelations(new ArrayList<>()).build());
        tagList.add(Tag.builder().name(INTEREST10).billTagRelations(new ArrayList<>()).userTagRelations(new ArrayList<>()).build());
        tagList.add(Tag.builder().name(INTEREST11).billTagRelations(new ArrayList<>()).userTagRelations(new ArrayList<>()).build());
        tagList.add(Tag.builder().name(INTEREST12).billTagRelations(new ArrayList<>()).userTagRelations(new ArrayList<>()).build());
        tagList.add(Tag.builder().name(INTEREST13).billTagRelations(new ArrayList<>()).userTagRelations(new ArrayList<>()).build());
        tagList.add(Tag.builder().name(INTEREST14).billTagRelations(new ArrayList<>()).userTagRelations(new ArrayList<>()).build());
        tagList.add(Tag.builder().name(INTEREST15).billTagRelations(new ArrayList<>()).userTagRelations(new ArrayList<>()).build());
        tagList.add(Tag.builder().name(INTEREST16).billTagRelations(new ArrayList<>()).userTagRelations(new ArrayList<>()).build());
        tagList.add(Tag.builder().name(INTEREST17).billTagRelations(new ArrayList<>()).userTagRelations(new ArrayList<>()).build());
        tagRepository.saveAll(tagList);

        return tagList;
    }

    private void insertUserData() {
        User user = User.InitBuilder()
                .uuid(USER_UUID)
                .build();
        userRepository.save(user);
    }
}