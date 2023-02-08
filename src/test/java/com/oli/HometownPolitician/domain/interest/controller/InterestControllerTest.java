package com.oli.HometownPolitician.domain.interest.controller;

import com.oli.HometownPolitician.domain.interest.dto.InterestDto;
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
class InterestControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    @DisplayName("관심분야 리스트 반환됨을 확인")
    public void interests_well_test() {
        graphQlTester.documentName("interest")
                .operationName("queryInterests")
                .execute()
                .errors()
                .verify()
                .path("queryInterests.list")
                .entityList(InterestDto.class)
                .path("queryInterests.list[*].id")
                .entityList(Long.class)
                .path("queryInterests.list[*].name")
                .entityList(String.class);
    }

}