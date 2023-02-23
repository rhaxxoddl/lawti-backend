package com.oli.HometownPolitician.domain.billUserRelation.repository;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.bill.enumeration.BillStageType;
import com.oli.HometownPolitician.domain.bill.repository.BillRepository;
import com.oli.HometownPolitician.domain.billMessage.input.BillMessageRoomFilterInput;
import com.oli.HometownPolitician.domain.billMessage.input.BillMessageRoomListInput;
import com.oli.HometownPolitician.domain.billMessage.repository.BillMessageRepository;
import com.oli.HometownPolitician.domain.billTagRelation.repository.BillTagRelationRepository;
import com.oli.HometownPolitician.domain.billTagRelation.service.BillTagRelationProvider;
import com.oli.HometownPolitician.domain.billTagRelation.service.BillTagRelationService;
import com.oli.HometownPolitician.domain.billUserRelation.entity.BillUserRelation;
import com.oli.HometownPolitician.domain.committe.entity.Committee;
import com.oli.HometownPolitician.domain.committe.repository.CommitteeRepository;
import com.oli.HometownPolitician.domain.tag.dto.TagInput;
import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.tag.repository.TagRepository;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.oli.HometownPolitician.domain.user.repository.UserRepository;
import com.oli.HometownPolitician.global.argument.input.TargetSlicePaginationInput;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BillUserRelationRepositoryImplTest {
    private final String USER1_UUID = "asfasd-asdfasd-fasdf-qwe";
    private final String USER2_UUID = "asfasd-asdfasd-fasdfqwef";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CommitteeRepository committeeRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private BillUserRelationRepository billUserRelationRepository;
    @Autowired
    private BillTagRelationRepository billTagRelationRepository;
    @Autowired
    private BillTagRelationService billTagRelationService;
    @Autowired
    private BillTagRelationProvider billTagRelationProvider;
    @Autowired
    private BillMessageRepository billMessageRepository;

    //TODO 테스트를 파일단위로 실행할 경우 첫번째 테스트 이후 테스트들이 모두 오류
    @BeforeEach
    void setUp() {
        List<User> users = userRepository.findAll();
        List<User> userList = insertUserData();
        List<Tag> tagList = insertTagData();
        List<Committee> committeeList = insertCommitteeData();
        List<Bill> billList = insertBillData(committeeList);
        connectBillTag(billList);
        connectUserBill(userList, billList);
        insertBillMessageDataFirst(billList);
        em.flush();
        em.clear();
    }

    @AfterEach
    void clear() {
        em.flush();
        billUserRelationRepository.deleteAll();
        billTagRelationRepository.deleteAll();
        billMessageRepository.deleteAll();
        billRepository.deleteAll();
        tagRepository.deleteAll();
        committeeRepository.deleteAll();
        userRepository.deleteAll();
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("input에 tagIdList에 요소가 있을 경우 해당 태그 리스트에 해당하는 billUserRelation만 잘 나오는지 확인")
    void qFindByUserUuidAndFilter_exist_tagIdList_well_test() {
        BillMessageRoomListInput input = getBillMessageRoomListInputExistTag();
        List<BillUserRelation> billUserRelations = billUserRelationRepository.qFindByUserUuidAndFilter(input, USER1_UUID);

        assertThat(billUserRelations).isNotNull();
        assertThat(billUserRelations.size()).isEqualTo(1);
        assertThat(billUserRelations.get(0).getBill().getTitle()).isEqualTo("test title middle");
        assertThat(billUserRelations.get(1).getBill().getTitle()).isEqualTo("test title right");
    }

    @Test
    @DisplayName("input에 tagIdList에 요소가 없을 경우 모든 billUserRelation가 잘 나오는지 확인")
    void qFindByUserUuidAndFilter_not_exist_tagIdList_well_test() {
        BillMessageRoomListInput input = getBillMessageRoomListInputNotExistTag();
        List<BillUserRelation> billUserRelations = billUserRelationRepository.qFindByUserUuidAndFilter(input, USER1_UUID);
        billUserRelations.forEach(billUserRelation -> System.out.println(billUserRelation));

        assertThat(billUserRelations).isNotNull();
        assertThat(billUserRelations.size()).isEqualTo(2);
    }

    private List<TagInput> getTagList() {
        List<TagInput> tagList = new ArrayList<>();
        tagList.add(TagInput.builder()
                .id(1L)
                .name("국회, 인권")
                .build());
        return tagList;
    }

    private BillMessageRoomListInput getBillMessageRoomListInputExistTag() {
        return BillMessageRoomListInput.builder()
                .filter(
                        BillMessageRoomFilterInput.builder()
                                .tagList(getTagList())
                                .build()
                )
                .pagination(
                        TargetSlicePaginationInput.from(
                                null,
                                5,
                                true
                        )
                )
                .build();
    }

    private BillMessageRoomListInput getBillMessageRoomListInputNotExistTag() {
        return BillMessageRoomListInput.builder()
                .filter(
                        BillMessageRoomFilterInput.builder()
                                .tagList(new ArrayList<>())
                                .build()
                )
                .pagination(
                        TargetSlicePaginationInput.from(
                                null,
                                5,
                                true
                        )
                )
                .build();
    }

    private List<User> insertUserData() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(USER1_UUID));
        userList.add(new User(USER2_UUID));
        userRepository.saveAll(userList);

        return userList;
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


    private List<Bill> insertBillData(List<Committee> committeeList) {
        List<Bill> bills = new ArrayList<>();
        bills.add(Bill.builder()
                .title("test title right")
                .externalBillId("testExternalBillId")
                .number(123456L)
                .proposeDate(LocalDate.now())
                .committee(committeeList.get(0))
                .committeeDate(LocalDate.now())
                .currentStage(BillStageType.RECEIPT)
                .noticeEndDate(LocalDate.now())
                .plenaryProcessingDate(null)
                .plenaryResult(null)
                .proposeAssembly(21L)
                .summary("test summary")
                .billPdfUri("test billPdfUri")
                .tags(new ArrayList<>())
                .followedBillUserRelations(new ArrayList<>())
                .alternativeBill(null)
                .build());
        bills.add(Bill.builder()
                .title("test title left")
                .externalBillId("testExternalBillId2")
                .number(1234567L)
                .proposeDate(LocalDate.now())
                .committee(committeeList.get(1))
                .committeeDate(LocalDate.now())
                .currentStage(BillStageType.RECEIPT)
                .noticeEndDate(LocalDate.now())
                .plenaryProcessingDate(null)
                .plenaryResult(null)
                .proposeAssembly(21L)
                .summary("test summary")
                .billPdfUri("test billPdfUri")
                .tags(new ArrayList<>())
                .followedBillUserRelations(new ArrayList<>())
                .alternativeBill(null)
                .build());
        bills.add(Bill.builder()
                .title("test title middle")
                .externalBillId("testExternalBillId3")
                .number(1234568L)
                .proposeDate(LocalDate.now())
                .committee(committeeList.get(2))
                .committeeDate(LocalDate.now())
                .currentStage(BillStageType.RECEIPT)
                .noticeEndDate(LocalDate.now())
                .plenaryProcessingDate(null)
                .plenaryResult(null)
                .proposeAssembly(21L)
                .summary("test summary")
                .billPdfUri("test billPdfUri")
                .tags(new ArrayList<>())
                .followedBillUserRelations(new ArrayList<>())
                .alternativeBill(null)
                .build());
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
                    .external_committee_id((12345L + i.toString()))
                    .build());
        }
        committeeRepository.saveAll(committeeList);

        return committeeList;
    }

    private void connectBillTag(List<Bill> billList) {
        billList.forEach(bill ->
                billTagRelationProvider.matchTagByCommittee(bill.getCommittee())
                        .addBill(bill));
    }

    private void connectUserBill(List<User> userList, List<Bill> billList) {
        userList.get(0).followBills(billList);
        userList.get(0).unfollowBills(billList.subList(1, 2));
        userList.get(1).followBills(billList.subList(1, 2));
    }

    private void insertBillMessageDataFirst(List<Bill> bills) {
        bills.forEach(bill -> {
            bill.updateCurrentStage(BillStageType.COMMITTEE_RECEIPT);
        });
        bills.forEach(bill -> {
            bill.updateCurrentStage(BillStageType.COMMITTEE_REVIEW);
        });
        bills.get(0).updateCurrentStage(BillStageType.SYSTEMATIC_REVIEW);
        bills.get(2).updateCurrentStage(BillStageType.SYSTEMATIC_REVIEW);
        bills.get(2).updateCurrentStage(BillStageType.DISCARD);
    }
}