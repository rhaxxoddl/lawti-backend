package com.oli.HometownPolitician.domain.tag.controller;

import com.oli.HometownPolitician.domain.tag.dto.TagDto;
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
    @Autowired
    private GraphQlTester graphQlTester;

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

    //    TODO 해당 테스트는 현재 제대로 동작하지 않음. 수정필요
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
    }
}