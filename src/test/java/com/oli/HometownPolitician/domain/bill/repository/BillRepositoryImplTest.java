package com.oli.HometownPolitician.domain.bill.repository;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.bill.enumeration.BillStageType;
import com.oli.HometownPolitician.domain.billTagRelation.repository.BillTagRelationRepository;
import com.oli.HometownPolitician.domain.billTagRelation.service.BillTagRelationProvider;
import com.oli.HometownPolitician.domain.billUserRelation.repository.BillUserRelationRepository;
import com.oli.HometownPolitician.domain.committee.entity.Committee;
import com.oli.HometownPolitician.domain.committee.input.CommitteeInput;
import com.oli.HometownPolitician.domain.committee.repository.CommitteeRepository;
import com.oli.HometownPolitician.domain.politician.entity.Politician;
import com.oli.HometownPolitician.domain.politician.repository.PoliticianRepository;
import com.oli.HometownPolitician.domain.proposer.repository.ProposerRepository;
import com.oli.HometownPolitician.domain.search.enumeration.SearchResultOrderBy;
import com.oli.HometownPolitician.domain.search.input.SearchFilterInput;
import com.oli.HometownPolitician.domain.search.input.SearchInput;
import com.oli.HometownPolitician.domain.tag.dto.TagInput;
import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.tag.repository.TagRepository;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.oli.HometownPolitician.domain.user.repository.UserRepository;
import com.oli.HometownPolitician.global.argument.input.TargetSlicePaginationInput;
import com.oli.HometownPolitician.global.error.NotFoundError;
import com.oli.HometownPolitician.global.tool.ListTool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BillRepositoryImplTest {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BillUserRelationRepository billUserRelationRepository;
    @Autowired
    private CommitteeRepository committeeRepository;
    @Autowired
    private BillTagRelationProvider billTagRelationProvider;
    @Autowired
    private BillTagRelationRepository billTagRelationRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private PoliticianRepository politicianRepository;
    @Autowired
    private ProposerRepository proposerRepository;
    @Autowired
    private EntityManager em;
    private final String UUID_PREFIX = "UUID-";

//    TODO 테스트 메서드 간에 분리가 되지 않아 DB 제약조건 오류가 남

    @BeforeEach
    void setUp() {
        List<Committee> committees = insertCommitteeData();
        List<Tag> tags = insertTagData();
        List<Bill> bills = insertBillData(committees);
        connectBillTag(bills);
        List<User> users = insertUserData();
        connectBillUser(bills, users);
        List<Politician> politicians = insertPolitician();
        connectBillPolitician(bills, politicians);
        em.flush();
        em.clear();
    }

    @AfterEach
    void clear() {
        proposerRepository.deleteAll();
        politicianRepository.deleteAll();
        billUserRelationRepository.deleteAll();
        userRepository.deleteAll();
        billTagRelationRepository.deleteAll();
        billRepository.deleteAll();
        tagRepository.deleteAll();
        committeeRepository.deleteAll();
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("filter, orderBy, 검색 키워드 없이 잘 조회되는지 확인")
    void queryBillsBySearchInput_not_exist_filter_orderBy_keyword_well_test() {
        SearchInput searchInput = getSearchInput(null, null, null, null, 10, true, SearchResultOrderBy.RECENTLY);
        List<Bill> bills = billRepository.queryBillsBySearchInput(searchInput);
        assertThat(bills).isNotNull();
        assertThat(bills.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("committee필터와 인기순 정렬조건을 걸었을 때 검색이 잘 되는지 확인")
    void queryBillsBySearchInput_exist_committee_well_test() {
        Committee committee = committeeRepository.findById(10L).orElseThrow(() -> new NotFoundError(""));
        SearchInput searchInput = getSearchInput(null, committee, null, null, 10, true, SearchResultOrderBy.POPULARITY);
        List<Bill> bills = billRepository.queryBillsBySearchInput(searchInput);
        assertThat(bills).isNotNull();
        assertThat(bills.size()).isGreaterThan(5);
        for (int i = 0; i < bills.size(); i++) {
            if (i != bills.size() - 1)
                assertThat(
                        bills.get(i).getFollowerCount())
                        .isLessThanOrEqualTo(bills.get(i + 1).getFollowerCount());
        }
    }

    @Test
    @DisplayName("인기순 정렬조건을 내림차순으로 걸었을 때 검색이 잘 되는지 확인")
    void queryBillsBySearchInput_popularity_desc_well_test() {
        SearchInput searchInput = getSearchInput(null, null, null, null, 10, false, SearchResultOrderBy.POPULARITY);
        List<Bill> bills = billRepository.queryBillsBySearchInput(searchInput);
        assertThat(bills).isNotNull();
        assertThat(bills.size()).isGreaterThan(5);
        for (int i = 0; i < bills.size(); i++) {
            if (i != bills.size() - 1)
                assertThat(
                        bills.get(i).getFollowerCount())
                        .isGreaterThanOrEqualTo(bills.get(i + 1).getFollowerCount());
        }
    }

    @Test
    @DisplayName("keyword에 국회운영위원회를 넣었을 때 검색이 잘 되는지 확인")
    void queryBillsBySearchInput_exist_keyword_committee_well_test() {
        SearchInput searchInput = getSearchInput("국회운영위원회", null, null, null, 10, true, SearchResultOrderBy.RECENTLY);
        List<Bill> bills = billRepository.queryBillsBySearchInput(searchInput);
        assertThat(bills).isNotNull();
        for (int i = 0; i < bills.size(); i++) {
            assertThat(bills.get(i).getCommittee().getName()).isEqualTo("국회운영위원회");
            if (i != bills.size() - 1)
                assertThat(
                        bills.get(i).getCreatedAt())
                        .isBeforeOrEqualTo(bills.get(i + 1).getCreatedAt());
        }
    }

    @Test
    @DisplayName("keyword에 국회의원 이름을 넣었을 때 검색이 잘 되는지 확인")
    void queryBillsBySearchInput_exist_keyword_politician_well_test() {
        SearchInput searchInput = getSearchInput("politician name10", null, null, null, 10, true, SearchResultOrderBy.RECENTLY);
        List<Bill> bills = billRepository.queryBillsBySearchInput(searchInput);
        assertThat(bills).isNotNull();
        for (int i = 0; i < bills.size(); i++) {
            assertThat(bills.get(i).getProposers().stream().anyMatch(proposer -> proposer.getPolitician().getName().contains("politician name10")));
            if (i != bills.size() - 1)
                assertThat(
                        bills.get(i).getUpdatedAt())
                        .isBeforeOrEqualTo(bills.get(i + 1).getUpdatedAt());
        }
    }

    @Test
    @DisplayName("인기순으로 페이지네이션이 잘 되는지 확인")
    void queryBillsBySearchInput_popularity_pagination_well_test() {
        SearchInput searchInput = getSearchInput(null, null, null, null, 10, true, SearchResultOrderBy.POPULARITY);
        List<Bill> results = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            List<Bill> bills = billRepository.queryBillsBySearchInput(searchInput);
            if (bills.isEmpty())
                break;
            results.addAll(bills);
            assertThat(bills).isNotNull();
            assertThat(bills.size()).isEqualTo(10);
            for (int j = 0; j < bills.size(); j++) {
                if (j != bills.size() - 1) {
                    Long count = bills.get(j).getFollowerCount();
                    Long afterCount = bills.get(j + 1).getFollowerCount();
                    assertThat(count).isLessThanOrEqualTo(afterCount);
                }
            }
            searchInput = getSearchInput(null, null, null, ListTool.getLastElement(bills).getId(), 10, true, SearchResultOrderBy.POPULARITY);
        }
        List<Bill> allBill = billRepository.findAll();
        allBill.removeAll(results);
        assertThat(allBill.size()).isEqualTo(0);
        assertThat(results.size()).isEqualTo(100);
    }

    @Test
    @DisplayName("국회의원 이름을 키워드로 검색했을 때 인기순으로 페이지네이션이 잘 되는지 확인")
    void queryBillsBySearchInput_exist_politician_name_popularity_pagination_well_test() {
        SearchInput searchInput = getSearchInput("politician name1", null, null, null, 10, true, SearchResultOrderBy.POPULARITY);
        List<Bill> results = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            List<Bill> bills = billRepository.queryBillsBySearchInput(searchInput);
            if (bills.isEmpty())
                break;
            results.addAll(bills);
            assertThat(bills).isNotNull();
            assertThat(bills.size()).isEqualTo(10);
            for (int j = 0; j < bills.size(); j++) {
                if (j != bills.size() - 1) {
                    Long count = bills.get(j).getFollowerCount();
                    Long afterCount = bills.get(j + 1).getFollowerCount();
                    assertThat(count).isLessThanOrEqualTo(afterCount);
                }
                assertThat(bills.get(j).getProposers().stream().anyMatch(proposer -> proposer.getPolitician().getName().contains("politician name10")));
            }
            searchInput = getSearchInput(null, null, null, ListTool.getLastElement(bills).getId(), 10, true, SearchResultOrderBy.POPULARITY);
        }
        List<Bill> allBill = billRepository.findAll();
        allBill.removeAll(results);
        assertThat(allBill.size() + results.size()).isEqualTo(100);
    }

    private SearchInput getSearchInput(String keyword, Committee committee, Tag tag, Long target, int elementSize, boolean isAscending, SearchResultOrderBy orderBy) {
        return SearchInput.builder()
                .keyword(keyword)
                .filter(
                        getSearchFilterInput(committee, tag)
                )
                .pagination(
                        TargetSlicePaginationInput
                                .builder()
                                .target(target)
                                .elementSize(elementSize)
                                .isAscending(isAscending)
                                .build()
                )
                .orderBy(orderBy)
                .build();
    }

    private SearchFilterInput getSearchFilterInput(Committee committee, Tag tag) {
        return SearchFilterInput
                .builder()
                .committee(
                        committee == null ? null : CommitteeInput
                                .builder()
                                .committeeId(committee.getId())
                                .build()
                )
                .tag(
                        tag == null ? null : TagInput
                                .builder()
                                .id(tag.getId())
                                .name(tag.getName())
                                .build()
                )
                .build();
    }

    private void connectBillUser(List<Bill> bills, List<User> users) {
        for (int i = 0; i < 100; i++) {
            List<Integer> startIdxAndEndIdx = getRandomIndex();
            int startIdx = startIdxAndEndIdx.get(0);
            int endIdx = startIdxAndEndIdx.get(1);
            List<Bill> subBillList = bills.subList(startIdx, endIdx);
            users.get(i).followBills(subBillList);
        }
    }

    private List<User> insertUserData() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            users.add(User.builder()
                    .uuid(UUID_PREFIX + UUID.randomUUID())
                    .build());
        }
        userRepository.saveAll(users);
        return users;
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

    private List<Integer> getRandomIndex() {
        Random random = new Random();
        List<Integer> startIdxAndEndIdx = new ArrayList<>();
        int randomIdx1 = random.nextInt(100);
        int randomIdx2 = random.nextInt(randomIdx1, 100);
        startIdxAndEndIdx.add(randomIdx1);
        startIdxAndEndIdx.add(randomIdx2);
        return startIdxAndEndIdx;
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
}