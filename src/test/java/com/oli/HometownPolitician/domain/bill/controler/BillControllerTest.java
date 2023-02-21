package com.oli.HometownPolitician.domain.bill.controler;

import com.oli.HometownPolitician.domain.bill.dto.BillDto;
import com.oli.HometownPolitician.domain.bill.dto.CommitteeDto;
import com.oli.HometownPolitician.domain.bill.dto.ProposersDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureGraphQlTester
@Transactional
class BillControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    @DisplayName("queryBillDetail이 잘 호출되는지 확인")
    void queryBillDetail_well_test() {
        Map<String, Object> input = new ConcurrentHashMap<>() {{
            put("billId", 1L);
        }};
        graphQlTester.documentName("bill")
                .operationName("queryBillDetail")
                .variable("input", input)
                .execute()
                .errors()
                .verify()
                .path("queryBillDetail.id")
                .entity(Long.class)
                .path("queryBillDetail.title")
                .entity(String.class)
                .path("queryBillDetail.currentStage")
                .entity(String.class)
                .path("queryBillDetail.summary")
                .entity(String.class)
                .path("queryBillDetail.proposers")
                .entity(ProposersDto.class)
                .path("queryBillDetail.proposerDate")
                .entity(LocalDate.class)
                .path("queryBillDetail.committee")
                .entity(CommitteeDto.class)
                .path("queryBillDetail.committeDate")
                .entity(LocalDate.class);
    }

    @Test
    @DisplayName("followBills이 잘 호출되는지 확인")
    void followBills_well_test() {
        graphQlTester.documentName("bill")
                .operationName("followBills")
                .execute()
                .errors()
                .verify()
                .path("followBills.followingBills")
                .entityList(BillDto.class)
                .path("followBills.followingBills.[*].billId")
                .entityList(Long.class)
                .path("followBills.followingBills.[*].title")
                .entityList(String.class);
    }

    @Test
    @DisplayName("unfollowBills이 잘 호출되는지 확인")
    void unfollowBills_well_test() {
        graphQlTester.documentName("bill")
                .operationName("unfollowBills")
                .execute()
                .errors()
                .verify()
                .path("unfollowBills.followingBills")
                .entityList(BillDto.class)
                .path("unfollowBills.followingBills.[*].billId")
                .entityList(Long.class)
                .path("unfollowBills.followingBills.[*].title")
                .entityList(String.class);
    }
}