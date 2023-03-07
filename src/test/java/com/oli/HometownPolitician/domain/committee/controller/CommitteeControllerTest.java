package com.oli.HometownPolitician.domain.committee.controller;

import com.oli.HometownPolitician.domain.committee.dto.CommitteeDto;
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
class CommitteeControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;
    @Test
    public void queryCommittees_well_test() {
        graphQlTester.documentName("committee")
                .operationName("queryCommittees")
                .execute()
                .errors()
                .verify()
                .path("queryCommittees.list")
                .entityList(CommitteeDto.class)
                .path("queryCommittees.list[*].committeeId")
                .entityList(Long.class)
                .path("queryCommittees.list[*].name")
                .entityList(String.class);
    }
}