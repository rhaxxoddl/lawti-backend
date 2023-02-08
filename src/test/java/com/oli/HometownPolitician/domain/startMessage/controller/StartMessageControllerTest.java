package com.oli.HometownPolitician.domain.startMessage.controller;

import com.oli.HometownPolitician.domain.startMessage.dto.StartMessageDto;
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
class StartMessageControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;


    @Test
    @DisplayName("[CONTROLLER] 로티 시작메시지가 반환됨을 확인")
    public void start_message_controller_well_test() {
        graphQlTester.documentName("start-message")
                .operationName("queryStartMessages")
                .execute()
                .errors()
                .verify()
                .path("queryStartMessages.list")
                .entityList(StartMessageDto.class)
                .isNotEqualTo(null)
                .path("queryStartMessages.list[*].id")
                .entityList(Long.class)
                .isNotEqualTo(null)
                .path("queryStartMessages.list[*].message")
                .entityList(String.class)
                .isNotEqualTo(null);
    }
}