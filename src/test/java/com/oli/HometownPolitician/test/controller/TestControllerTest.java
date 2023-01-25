package com.oli.HometownPolitician.test.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureGraphQlTester
class TestControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Autowired
    Environment env;

    @Test
    @DisplayName("GraphQL 기본 Query 동작 확인")
    void defaultQuery() throws Exception {
        graphQlTester.documentName("default")
                .operationName("ThisIsForBaseQueryDoNotTouchThisMethod")
                .execute();
    }

    @Test
    @DisplayName("GraphQL 기본 Mutation 동작 확인")
    void defaultMutation() {
        graphQlTester.documentName("default")
                .operationName("ThisIsForBaseMutationDoNotTouchThisMethod")
                .execute();
    }

}