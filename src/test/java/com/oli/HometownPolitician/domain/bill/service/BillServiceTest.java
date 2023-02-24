package com.oli.HometownPolitician.domain.bill.service;

import com.oli.HometownPolitician.domain.bill.dto.BillDetailDto;
import com.oli.HometownPolitician.domain.bill.dto.BillDto;
import com.oli.HometownPolitician.domain.bill.dto.FollowingBillsDto;
import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.bill.enumeration.BillStageType;
import com.oli.HometownPolitician.domain.bill.input.BillInput;
import com.oli.HometownPolitician.domain.bill.input.BillsInput;
import com.oli.HometownPolitician.domain.bill.repository.BillRepository;
import com.oli.HometownPolitician.domain.billUserRelation.entity.BillUserRelation;
import com.oli.HometownPolitician.domain.billUserRelation.repository.BillUserRelationRepository;
import com.oli.HometownPolitician.domain.committee.entity.Committee;
import com.oli.HometownPolitician.domain.committee.repository.CommitteeRepository;
import com.oli.HometownPolitician.domain.politician.entity.Politician;
import com.oli.HometownPolitician.domain.politician.repository.PoliticianRepository;
import com.oli.HometownPolitician.domain.proposer.entity.Proposer;
import com.oli.HometownPolitician.domain.proposer.enumeration.ProposerRole;
import com.oli.HometownPolitician.domain.proposer.repository.ProposerRepository;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.oli.HometownPolitician.domain.user.repository.UserRepository;
import com.oli.HometownPolitician.domain.user.service.UserService;
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

import static org.assertj.core.api.Assertions.assertThat;
// TODO queryBillsByIdList_well_test()를 제외한 나머지 테스트 통합 실행시 에러남
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class BillServiceTest {
    private final String USER_UUID = "asfasd-asdfasd-fasdf-qwe";
    private final String BEARER_PREFIX = "Bearer ";
    private final String UUID_PREFIX = "UUID-";
    private final String AUTHORIZATION = BEARER_PREFIX + UUID_PREFIX + USER_UUID;
    @Autowired
    private BillService billService;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private PoliticianRepository politicianRepository;
    @Autowired
    private CommitteeRepository committeeRepository;
    @Autowired
    private ProposerRepository proposerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BillUserRelationRepository billUserRelationRepository;
    @Autowired
    private EntityManager em;

    @BeforeEach
    void setUp() {
        List<Bill> bills = initBill();
        List<Politician> politicians = initPoliticians();
        Committee committee = initCommittee();
        connectBillsPoliticians(politicians, bills);
        connectBillsCommittee(bills, committee);
        initUser();
    }

    @AfterEach
    void clear() {
        proposerRepository.deleteAll();
        billRepository.deleteAll();
        politicianRepository.deleteAll();
        committeeRepository.deleteAll();
        userRepository.deleteAll();
        em.clear();
    }

    @Test
    @DisplayName("Bill의 상세정보를 BillDetailDto로 잘 가져오는지 확인")
    void queryBillDetail_well_test() {
        BillInput billInput = BillInput.builder()
                .billId(1L)
                .build();
        BillDetailDto billDetailDto = billService.queryBillDetail(billInput);
        assertThat(billDetailDto).isNotNull();
        assertThat(billDetailDto.getClass()).isEqualTo(BillDetailDto.class);
    }

    @Test
    @DisplayName("BillsInput을 입력하여 해당하는 bill 리스트를 잘 출력하는지 확인")
    void queryBillsByIdList_well_test() {
        BillsInput billsInput = getBillsInput();

        List<Bill> bills = billService.queryBillsByBillsInput(billsInput);
        assertThat(bills).isNotNull();
        assertThat(bills.size()).isEqualTo(2);
        assertThat(bills.get(0).getClass()).isEqualTo(Bill.class);
    }

    @Test
    @DisplayName("유저가 현재 팔로우하고 있는 bill 리스트를 잘 출력하는지 확인")
    void queryFollowingBills_well_test() {
        User user = userService.getUser(AUTHORIZATION);
        BillsInput billsInput = getBillsInput();
        List<Bill> bills = billService.queryBillsByBillsInput(billsInput);
        assertThat(bills).isNotNull();
        assertThat(bills.size()).isEqualTo(2);
        assertThat(bills.get(0).getClass()).isEqualTo(Bill.class);

        List<BillUserRelation> followedBillUserRelations = user.getFollowedBillUserRelations();
        assertThat(followedBillUserRelations).isNotNull();
        assertThat(followedBillUserRelations.size()).isEqualTo(0);

        followedBillUserRelations.add(BillUserRelation.builder()
                .user(user)
                .bill(bills.get(0))
                .build());
        followedBillUserRelations.add(BillUserRelation.builder()
                .user(user)
                .bill(bills.get(1))
                .build());
        billUserRelationRepository.saveAll(followedBillUserRelations);

        FollowingBillsDto followingBillsDto = billService.queryFollowingBills(AUTHORIZATION);
        assertThat(followingBillsDto).isNotNull();
        assertThat(followingBillsDto.getClass()).isEqualTo(FollowingBillsDto.class);
        assertThat(followingBillsDto.getList()).isNotNull();
        assertThat(followingBillsDto.getList().size()).isEqualTo(2);
        assertThat(followingBillsDto.getList().get(0).getClass()).isEqualTo(BillDto.class);
        assertThat(followingBillsDto.getList().get(0).getBillId().getClass()).isEqualTo(Long.class);
        assertThat(followingBillsDto.getList().get(0).getTitle().getClass()).isEqualTo(String.class);
    }

    @Test
    @DisplayName("BillsInput을 입력하여 해당하는 bill을 잘 팔로우하고 팔로우 중인 bill 리스트를 잘 출력하는지 확인")
    void followBills_well() {
        BillsInput billsInput = getBillsInput();
        FollowingBillsDto followingBillsDtoBefore = billService.queryFollowingBills(AUTHORIZATION);
        assertThat(followingBillsDtoBefore).isNotNull();
        assertThat(followingBillsDtoBefore.getList()).isNotNull();
        assertThat(followingBillsDtoBefore.getList().size()).isEqualTo(0);

        FollowingBillsDto followingBillsDtoAfter = billService.followBills(billsInput, AUTHORIZATION);
        assertThat(followingBillsDtoAfter).isNotNull();
        assertThat(followingBillsDtoAfter.getList()).isNotNull();
        assertThat(followingBillsDtoAfter.getList().size()).isEqualTo(2);
        assertThat(followingBillsDtoAfter.getList().get(0).getClass()).isEqualTo(BillDto.class);
        assertThat(followingBillsDtoAfter.getList().get(0).getBillId().getClass()).isEqualTo(Long.class);
        assertThat(followingBillsDtoAfter.getList().get(0).getTitle().getClass()).isEqualTo(String.class);
    }

    @Test
    @DisplayName("BillsInput을 입력하여 해당하는 bill을 잘 언팔로우하고 팔로우 중인 bill 리스트를 잘 출력하는지 확인")
    void unfollowBills_well_test() {
        User user = userService.getUser(AUTHORIZATION);
        BillsInput billsInput = getBillsInput();
        List<Bill> bills = billService.queryBillsByBillsInput(billsInput);
        assertThat(bills).isNotNull();
        assertThat(bills.size()).isEqualTo(2);
        assertThat(bills.get(0).getClass()).isEqualTo(Bill.class);

        List<BillUserRelation> followedBillUserRelations = user.getFollowedBillUserRelations();
        assertThat(followedBillUserRelations).isNotNull();
        assertThat(followedBillUserRelations.size()).isEqualTo(0);

        followedBillUserRelations.add(BillUserRelation.builder()
                .user(user)
                .bill(bills.get(0))
                .build());
        followedBillUserRelations.add(BillUserRelation.builder()
                .user(user)
                .bill(bills.get(1))
                .build());
        billUserRelationRepository.saveAll(followedBillUserRelations);
        FollowingBillsDto unfollowingBillsDtoBefore = billService.queryFollowingBills(AUTHORIZATION);
        assertThat(unfollowingBillsDtoBefore).isNotNull();
        assertThat(unfollowingBillsDtoBefore.getList()).isNotNull();
        assertThat(unfollowingBillsDtoBefore.getList().size()).isEqualTo(2);

        billsInput.getList().remove(1);
        FollowingBillsDto unfollowingBillsDtoAfter = billService.unfollowBills(billsInput, AUTHORIZATION);
        assertThat(unfollowingBillsDtoAfter).isNotNull();
        assertThat(unfollowingBillsDtoAfter.getList()).isNotNull();
        assertThat(unfollowingBillsDtoAfter.getList().size()).isEqualTo(1);
        assertThat(unfollowingBillsDtoAfter.getList().get(0).getClass()).isEqualTo(BillDto.class);
        assertThat(unfollowingBillsDtoAfter.getList().get(0).getBillId().getClass()).isEqualTo(Long.class);
        assertThat(unfollowingBillsDtoAfter.getList().get(0).getTitle().getClass()).isEqualTo(String.class);
    }


    private List<Bill> initBill() {
        List<Bill> bills = new ArrayList<>();
        bills.add(Bill.builder()
                .id(1L)
                .title("test title")
                .externalBillId("testExternalBillId")
                .number(123456L)
                .proposeDate(LocalDate.now())
                .committee(null)
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
                .title("test title2")
                .externalBillId("testExternalBillId2")
                .number(1234567L)
                .proposeDate(LocalDate.now())
                .committee(null)
                .committeeDate(LocalDate.now())
                .currentStage(BillStageType.PROMULGATION)
                .noticeEndDate(LocalDate.now())
                .plenaryProcessingDate(null)
                .plenaryResult(null)
                .proposeAssembly(20L)
                .summary("test summary")
                .billPdfUri("test billPdfUri")
                .alternativeBill(null)
                .build());
        billRepository.saveAll(bills);
        return bills;
    }
    private List<Politician> initPoliticians() {
        List<Politician> politicians = new ArrayList<>();
        politicians.add(Politician.builder()
                .name("홍준표")
                .chineseName("洪準杓")
                .party("무소속")
                .build());
        politicians.add(Politician.builder()
                .name("안철수")
                .chineseName("安哲秀")
                .party("국민의당")
                .build());
        politicians.add(Politician.builder()
                .name("이재명")
                .chineseName("洪準杓")
                .party("더불어민주당")
                .build());
        politicians.add(Politician.builder()
                .name("심상정")
                .chineseName("沈相奵")
                .party("정의당")
                .build());
        politicianRepository.saveAll(politicians);
        return politicians;
    }

    private Committee initCommittee() {
        Committee committee = Committee.builder()
                .external_committee_id("test committee id")
                .name("test name")
                .build();
        committeeRepository.save(committee);
        return committee;
    }

    private void connectBillsCommittee(List<Bill> bills, Committee committee) {
        bills.get(0).setCommittee(committee);
        bills.get(1).setCommittee(committee);
        billRepository.saveAll(bills);
    }
    private void connectBillsPoliticians(List<Politician> politicians, List<Bill> bills) {
        List<Proposer> proposers1 = new ArrayList<>();
        List<Proposer> proposers2 = new ArrayList<>();
        proposers1.add(Proposer.builder()
                .bill(bills.get(0))
                .politician(politicians.get(0))
                .proposerRole(ProposerRole.REPRESENTATIVE)
                .build());
        proposers1.add(Proposer.builder()
                .bill(bills.get(0))
                .politician(politicians.get(1))
                .proposerRole(ProposerRole.PUBLIC)
                .build());
        proposers2.add(Proposer.builder()
                .bill(bills.get(1))
                .politician(politicians.get(2))
                .proposerRole(ProposerRole.REPRESENTATIVE)
                .build());
        proposers2.add(Proposer.builder()
                .bill(bills.get(1))
                .politician(politicians.get(2))
                .proposerRole(ProposerRole.PUBLIC)
                .build());
        proposerRepository.saveAll(proposers1);
        proposerRepository.saveAll(proposers2);
        bills.get(0).setProposers(proposers1);
        bills.get(1).setProposers(proposers2);
        billRepository.saveAll(bills);
    }
    private BillsInput getBillsInput() {
        List<BillInput> billInputs = new ArrayList<>();
        billInputs.add(BillInput.builder().billId(1L).build());
        billInputs.add(BillInput.builder().billId(2L).build());
        return BillsInput.builder().list(billInputs).build();
    }

    private void initUser() {
        User user = new User(USER_UUID);
        userRepository.save(user);
    }

}
