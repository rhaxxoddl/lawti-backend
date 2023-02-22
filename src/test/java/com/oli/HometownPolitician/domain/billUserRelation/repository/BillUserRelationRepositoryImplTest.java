package com.oli.HometownPolitician.domain.billUserRelation.repository;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.bill.enumeration.BillStageType;
import com.oli.HometownPolitician.domain.bill.repository.BillRepository;
import com.oli.HometownPolitician.domain.billMessage.input.BillMessageRoomFilterInput;
import com.oli.HometownPolitician.domain.billMessage.input.BillMessageRoomListInput;
import com.oli.HometownPolitician.domain.billTagRelation.repository.BillTagRelationRepository;
import com.oli.HometownPolitician.domain.billTagRelation.service.BillTagRelationService;
import com.oli.HometownPolitician.domain.billUserRelation.entity.BillUserRelation;
import com.oli.HometownPolitician.domain.committe.entity.Committee;
import com.oli.HometownPolitician.domain.committe.repository.CommitteeRepository;
import com.oli.HometownPolitician.domain.tag.dto.TagInput;
import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.tag.repository.TagRepository;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.oli.HometownPolitician.domain.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
        List<User> userList = insertUserData();
        List<Tag> tagList = insertTagData();
        List<Committee> committeeList = insertCommitteeData();
        List<Bill> billList = insertBillData(committeeList);
        connectBillTag(billList);
        connectUserBill(userList, billList);
    }

    @AfterEach
    void clear() {
        billUserRelationRepository.deleteAll();
        billTagRelationRepository.deleteAll();
        billRepository.deleteAll();
        tagRepository.deleteAll();
        committeeRepository.deleteAll();
        userRepository.deleteAll();
        em.clear();
    }

    @Test
    void qFindByUserUuidAndFilter_well_test() {
        List<BillUserRelation> billUserRelations = billUserRelationRepository.qFindByUserUuidAndFilter(getBillMessageRoomListInput(), USER1_UUID);
        assertThat(billUserRelations).isNotNull();
    }

    private List<TagInput> getTagList() {
        List<TagInput> tagList = new ArrayList<>();
        tagList.add(TagInput.builder()
                .id(1L)
                .name("국회, 인권")
                .build());
        return tagList;
    }

    private BillMessageRoomListInput getBillMessageRoomListInput() {
        return BillMessageRoomListInput.builder()
                .filter(
                        BillMessageRoomFilterInput.builder()
                                .tagList(getTagList())
                                .build()
                )
                .pagination(null)
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
        tagList.add(Tag.builder().name(INTEREST1).build());
        tagList.add(Tag.builder().name(INTEREST2).build());
        tagList.add(Tag.builder().name(INTEREST3).build());
        tagList.add(Tag.builder().name(INTEREST4).build());
        tagList.add(Tag.builder().name(INTEREST5).build());
        tagList.add(Tag.builder().name(INTEREST6).build());
        tagList.add(Tag.builder().name(INTEREST7).build());
        tagList.add(Tag.builder().name(INTEREST8).build());
        tagList.add(Tag.builder().name(INTEREST9).build());
        tagList.add(Tag.builder().name(INTEREST10).build());
        tagList.add(Tag.builder().name(INTEREST11).build());
        tagList.add(Tag.builder().name(INTEREST12).build());
        tagList.add(Tag.builder().name(INTEREST13).build());
        tagList.add(Tag.builder().name(INTEREST14).build());
        tagList.add(Tag.builder().name(INTEREST15).build());
        tagList.add(Tag.builder().name(INTEREST16).build());
        tagList.add(Tag.builder().name(INTEREST17).build());
        tagRepository.saveAll(tagList);

        return tagList;
    }


    private List<Bill> insertBillData(List<Committee> committeeList) {
        List<Bill> bills = new ArrayList<>();
        bills.add(Bill.builder()
                .id(1L)
                .title("test title right")
                .externalBillId("testExternalBillId")
                .number(123456L)
                .proposeDate(LocalDate.now())
                .committee(committeeList.get(0))
                .committeeDate(LocalDate.now())
                .currentStage(BillStageType.COMMITTEE_RECEIPT)
                .noticeEndDate(LocalDate.now())
                .plenaryProcessingDate(null)
                .plenaryResult(null)
                .proposeAssembly(21L)
                .summary("test summary")
                .billPdfUri("test billPdfUri")
                .alternativeBill(null)
                .build());
        bills.add(Bill.builder()
                .id(2L)
                .title("test title left")
                .externalBillId("testExternalBillId2")
                .number(1234567L)
                .proposeDate(LocalDate.now())
                .committee(committeeList.get(1))
                .committeeDate(LocalDate.now())
                .currentStage(BillStageType.PROMULGATION)
                .noticeEndDate(LocalDate.now())
                .plenaryProcessingDate(null)
                .plenaryResult(null)
                .proposeAssembly(21L)
                .summary("test summary")
                .billPdfUri("test billPdfUri")
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
        billList.forEach(bill -> billTagRelationService.createeBillTagRelationByCommittee(bill));
    }

    private void connectUserBill(List<User> userList, List<Bill> billList) {
        List<BillUserRelation> billUserRelationList = new ArrayList<>();
        billUserRelationList.add(BillUserRelation.builder()
                .id(1L)
                .user(userList.get(0))
                .bill(billList.get(0))
                .isUnfollowed(false)
                .build());
        billUserRelationList.add(BillUserRelation.builder()
                .id(2L)
                .user(userList.get(0))
                .bill(billList.get(1))
                .isUnfollowed(true)
                .build());
        billUserRelationList.add(BillUserRelation.builder()
                .id(3L)
                .user(userList.get(0))
                .bill(billList.get(1))
                .isUnfollowed(false)
                .build());

        billUserRelationRepository.saveAll(billUserRelationList);
    }
}