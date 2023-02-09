package com.oli.HometownPolitician.domain.user.controller;

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
class UserControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    @DisplayName("queryToken TokenDto가 잘 반환됨을 확인")
    public void queryAccessToken_well_test() {
        graphQlTester.documentName("user")
                .execute()
                .errors()
                .verify()
                .path("queryToken.accessToken")
                .entity(String.class);
    }


}