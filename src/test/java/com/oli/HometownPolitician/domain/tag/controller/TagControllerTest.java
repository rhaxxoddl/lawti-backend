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

}