package com.oli.HometownPolitician.domain.bill.entity;


import com.oli.HometownPolitician.domain.bill.enumeration.BillStageType;
import com.oli.HometownPolitician.domain.bill.enumeration.PlenaryResultType;
import com.oli.HometownPolitician.domain.bill.responseEntity.publicData.getBillInfoList.BillInfo;
import com.oli.HometownPolitician.domain.billMessage.entity.BillMessage;
import com.oli.HometownPolitician.domain.billTagRelation.entity.BillTagRelation;
import com.oli.HometownPolitician.domain.billUserRelation.entity.BillUserRelation;
import com.oli.HometownPolitician.domain.committee.entity.Committee;
import com.oli.HometownPolitician.domain.politician.entity.Politician;
import com.oli.HometownPolitician.domain.proposer.entity.Proposer;
import com.oli.HometownPolitician.domain.proposer.enumeration.ProposerRole;
import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
import io.jsonwebtoken.lang.Assert;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "bills")
@SequenceGenerator(name = "BILL_SEQ_GENERATOR", sequenceName = "BILL_SEQ")
public class Bill extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BILL_SEQ")
    @Column(name = "bill_id", unique = true, nullable = false)
    private Long id;
    @Column(name = "bill_external_id", unique = true, nullable = false)
    private String billExternalId;
    @Column(name = "number", nullable = false)
    private int number;
    @Column(name = "title", nullable = false)
    private String title;
    @Builder.Default
    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Proposer> proposers = new ArrayList<>();
    @Column(name = "propose_date", nullable = false)
    private LocalDate proposeDate;
    @Column(name = "propose_assembly")
    private Long proposeAssembly;
    @Lob
    @Column(name = "summary")
    private String summary;
    @Column(name = "bill_pdf_uri")
    private String billPdfUri;
    @Column(name = "notice_end_date")
    private LocalDate noticeEndDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "current_stage", nullable = false)
    private BillStageType currentStage;
    @Column(name = "committee_date")
    private LocalDate committeeDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "plenary_result")
    private PlenaryResultType plenaryResult;
    @Column(name = "plenary_processing_date")
    private LocalDate plenaryProcessingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "committee_id", referencedColumnName = "committee_id")
    private Committee committee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alternative_bill_id", referencedColumnName = "bill_id")
    private Bill alternativeBill;

    @Builder.Default
    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<BillUserRelation> billUserRelations = new ArrayList<>();
    @Builder.Default
    @Column(name = "follower_count")
    private Long followerCount = 0L;

    @Builder.Default
    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<BillMessage> billMessages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<BillTagRelation> billTagRelations = new ArrayList<>();

    @PrePersist
    public void setup() {
        if (this.billTagRelations == null)
            this.billTagRelations = new ArrayList<>();
        if (this.billUserRelations == null)
            this.billUserRelations = new ArrayList<>();
        if (this.billMessages == null)
            this.billMessages = new ArrayList<>();
        if (this.followerCount == null)
            this.followerCount = 0L;
    }

    @Builder(builderClassName = "InitBuilder", builderMethodName = "InitBuilder")
    public Bill(String billExternalId,
                int number,
                String title,
                LocalDate proposeDate,
                BillStageType currentStage,
                String summary) {
        Assert.notNull(billExternalId, "billExternalId에 null이 들어올 수 없습니다");
        Assert.notNull(number, "number에 null이 들어올 수 없습니다");
        Assert.notNull(title, "title에 null이 들어올 수 없습니다");
        Assert.notNull(proposeDate, "proposeDate에 null이 들어올 수 없습니다");
        if (currentStage == null)
            System.out.println("asfasdf");
        Assert.notNull(currentStage, "currentStage에 null이 들어올 수 없습니다");

        this.billExternalId = billExternalId;
        this.number = number;
        this.title = title;
        this.proposeDate = proposeDate;
        this.currentStage = currentStage;
        this.summary = summary;
    }

    static public Bill from(BillInfo billInfo) {
        return Bill.InitBuilder()
                .billExternalId(billInfo.getBillId())
                .number(billInfo.getBillNo())
                .title(billInfo.getBillName())
                .proposeDate(billInfo.getProposeDt())
                .currentStage(
                        BillStageType.valueOfLable(billInfo.getProcStageCd())
                )
                .summary(billInfo.getSummary())
                .build();
    }

    public void updateCurrentStage(BillStageType updateStage) {
        if (!this.currentStage.equals(updateStage)) {
            this.setCurrentStage(updateStage);
            BillMessage newBillMessage = BillMessage.ByCurrentStageBuilder()
                    .bill(this)
                    .currentStage(updateStage)
                    .build();
            this.billMessages.add(newBillMessage);
            // TODO 해당 Bill을 Follow하고 있는 사람에게 noti 보내는 로직 추가
        }
    }

    public void increaseFollowerCount() {
        this.followerCount++;
    }

    public void decreaseFollowerCount() {
        this.followerCount--;
    }

    public void addProposers(List<Politician> representativeProposers, List<Politician> publicProposers) {
        for (Politician politician : representativeProposers) {
            addProposer(politician, ProposerRole.REPRESENTATIVE);
        }
        for (Politician politician : publicProposers) {
            addProposer(politician, ProposerRole.PUBLIC);
        }
    }

    private void addProposer(Politician politician, ProposerRole role) {
        Proposer publicProposer = Proposer.InitBuilder()
                .bill(this)
                .politician(politician)
                .role(role)
                .build();
        this.proposers.add(publicProposer);
        politician.getProposeList().add(publicProposer);
    }
}
