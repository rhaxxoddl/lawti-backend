package com.oli.HometownPolitician.domain.bill.controler;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.bill.enumeration.BillStageType;
import com.oli.HometownPolitician.domain.bill.repository.BillRepository;
import com.oli.HometownPolitician.domain.billTagRelation.service.BillTagRelationProvider;
import com.oli.HometownPolitician.domain.committee.entity.Committee;
import com.oli.HometownPolitician.domain.committee.repository.CommitteeRepository;
import com.oli.HometownPolitician.domain.politician.entity.Politician;
import com.oli.HometownPolitician.domain.politician.repository.PoliticianRepository;
import com.oli.HometownPolitician.domain.proposer.repository.ProposerRepository;
import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.tag.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureGraphQlTester
@Transactional
class BillControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;
    @Autowired
    private BillTagRelationProvider billTagRelationProvider;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private CommitteeRepository committeeRepository;
    @Autowired
    private PoliticianRepository politicianRepository;
    @Autowired
    private ProposerRepository proposerRepository;

    @BeforeEach
    public void setup() {
        List<Committee> committees = insertCommitteeData();
        List<Tag> tags = insertTagData();
        List<Bill> bills = insertBillData(committees);
        connectBillTag(bills);
        List<Politician> politicians = insertPolitician();
        connectBillPolitician(bills, politicians);
    }

    @Test
    @DisplayName("queryBillDetail 잘 동작하는지 확인")
    public void queryBillDetail_well_test() {
        Map<String, Long> input = new ConcurrentHashMap<>() {{
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
                .path("queryBillDetail.committeeDate")
                .entity(LocalDate.class);
    }

    @Test
    @DisplayName("데이터가 없을 때 queryBillDetail에 NotFoundError 잘 반환되는지 확인")
    public void queryBillDetail_wrong_test() {
        Map<String, Object> input = new ConcurrentHashMap<>() {{
            put("billId", 1000L);
        }};
        graphQlTester.documentName("bill")
                .operationName("queryBillDetail")
                .variable("input", input)
                .execute()
                .errors()
                .expect(responseError -> responseError
                        .getErrorType()
                        .equals(ErrorType.NOT_FOUND)
                );
    }

    @Test
    @DisplayName("Authorization에 헤더 없이 followBills에서 에러를 반환하는지 확인")
    void followBills_wrong_test() {
        Map<String, Object> billInput1 = new ConcurrentHashMap<>() {{
            put("billId", 1L);
        }};
        Map<String, Object> billInput2 = new ConcurrentHashMap<>() {{
            put("billId", 2L);
        }};
        List<Map<String, Object>> list = Arrays.asList(billInput1, billInput2);
        Map<String, Object> input = Collections.singletonMap("list", list);
        graphQlTester.documentName("bill")
                .operationName("followBills")
                .variable("input", input)
                .execute()
                .errors()
                .expect(responseError -> responseError
                        .getErrorType()
                        .equals(ErrorType.NOT_FOUND));
    }

    @Test
    @DisplayName("unfollowBills이 잘 호출되는지 확인")
    void unfollowBills_well_test() {
        Map<String, Object> billInput1 = new ConcurrentHashMap<>() {{
            put("billId", 1L);
        }};
        Map<String, Object> billInput2 = new ConcurrentHashMap<>() {{
            put("billId", 2L);
        }};
        List<Map<String, Object>> list = Arrays.asList(billInput1, billInput2);
        Map<String, Object> input = Collections.singletonMap("list", list);
        graphQlTester.documentName("bill")
                .operationName("unfollowBills")
                .execute()
                .errors()
                .verify()
                .path("unfollowBills.list[*].billId")
                .entityList(Long.class)
                .path("unfollowBills.list[*].title")
                .entityList(String.class);
    }
    private List<Bill> insertBillData(List<Committee> committeeList) {
        List<Bill> bills = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            bills.add(Bill.builder()
                    .title("test title " + i)
                    .billExternalId("testExternalBillIds" + i)
                    .number(12341568 + i)
                    .proposeDate(LocalDate.now())
                    .committee(committeeList.get(i % committeeList.size()))
                    .committeeDate(LocalDate.now())
                    .currentStage(BillStageType.RECEIPT)
                    .noticeEndDate(LocalDate.now())
                    .plenaryProcessingDate(null)
                    .plenaryResult(null)
                    .proposeAssembly(21L)
                    .summary("test summary")
                    .billPdfUri("test billPdfUri")
                    .billTagRelations(new ArrayList<>())
                    .billUserRelations(new ArrayList<>())
                    .alternativeBill(null)
                    .build());
        }
        billRepository.saveAll(bills);
        return bills;
    }

    private List<Committee> insertCommitteeData() {
        List<String> committeeNameList = new ArrayList<>();
        committeeNameList.add("국회운영위원회");
        committeeNameList.add("법제사법위원회");
        committeeNameList.add("기획재정위원회");
        committeeNameList.add("교육위원회");
        committeeNameList.add("과학기술정보방송통신위원회");
        committeeNameList.add("외교통일위원회");
        committeeNameList.add("국방위원회");
        committeeNameList.add("행정안전위원회");
        committeeNameList.add("문화체육관광위원회");
        committeeNameList.add("국토교통위원회");
        committeeNameList.add("농림축산식품해양수산위원회");
        committeeNameList.add("산업통상자원중소벤처기업위원회");
        committeeNameList.add("보건복지위원회");
        committeeNameList.add("환경노동위원회");
        committeeNameList.add("정보위원회");
        committeeNameList.add("여성가족위원회");
        List<Committee> committeeList = new ArrayList<>();
        for (Long i = 0L; i < committeeNameList.size(); i++) {
            committeeList.add(Committee.builder()
                    .id(i + 1)
                    .name(committeeNameList.get(i.intValue()))
                    .externalCommitteeId((12345L + i.toString()))
                    .build());
        }
        committeeRepository.saveAll(committeeList);

        return committeeList;
    }

    private List<Tag> insertTagData() {
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

    private void connectBillTag(List<Bill> billList) {
        billList.forEach(bill ->
                billTagRelationProvider.matchTagByCommittee(bill.getCommittee())
                        .addBill(bill));
    }

    private List<Politician> insertPolitician() {
        List<Politician> politicians = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            politicians.add(
                    Politician.builder()
                            .name("politician name" + i)
                            .chineseName("politician chinese name" + i)
                            .party("party name" + i)
                            .build()
            );
        }
        politicianRepository.saveAll(politicians);
        return politicians;
    }

    private void connectBillPolitician(List<Bill> bills, List<Politician> politicians) {
        for (Bill bill : bills) {
            List<Integer> startIdxAndEndIdx = getRandomIndex();
            int startIdx = startIdxAndEndIdx.get(0);
            int endIdx = startIdxAndEndIdx.get(1);
            List<Politician> representativePoliticians = politicians.subList(1, 2);
            List<Politician> publicPolitician = politicians.subList(startIdx, endIdx);
            bill.addProposers(representativePoliticians, publicPolitician);
        }
    }

    private List<Integer> getRandomIndex() {
        Random random = new Random();
        List<Integer> startIdxAndEndIdx = new ArrayList<>();
        int randomIdx1 = random.nextInt(100);
        int randomIdx2 = random.nextInt(randomIdx1, 100);
        startIdxAndEndIdx.add(randomIdx1);
        startIdxAndEndIdx.add(randomIdx2);
        return startIdxAndEndIdx;
    }

}