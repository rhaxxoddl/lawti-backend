package com.oli.HometownPolitician.domain.bill.entity;


import com.oli.HometownPolitician.domain.bill.enumeration.BillStageType;
import com.oli.HometownPolitician.domain.bill.enumeration.PlenaryResultType;
import com.oli.HometownPolitician.domain.billMessage.entity.BillMessage;
import com.oli.HometownPolitician.domain.billTagRelation.entity.BillTagRelation;
import com.oli.HometownPolitician.domain.billUserRelation.entity.BillUserRelation;
import com.oli.HometownPolitician.domain.committee.entity.Committee;
import com.oli.HometownPolitician.domain.politician.entity.Politician;
import com.oli.HometownPolitician.domain.proposer.entity.Proposer;
import com.oli.HometownPolitician.domain.proposer.enumeration.ProposerRole;
import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
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
    private Long number;
    @Column(name = "title", nullable = false)
    private String title;
    @Builder.Default
    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Proposer> proposers = new ArrayList<>();
    @Column(name = "propose_date", nullable = false)
    private LocalDate proposeDate;
    @Column(name = "propose_assembly")
    private Long proposeAssembly;
    @Column(name = "summary", nullable = false)
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

    public void updateCurrentStage(BillStageType updateStage) {
        this.setCurrentStage(updateStage);
        BillMessage newBillMessage = BillMessage.ByCurrentStageBuilder()
                .bill(this)
                .currentStage(updateStage)
                .build();
        this.billMessages.add(newBillMessage);
    }

    public void increaseFollowerCount() {
        this.followerCount++;
    }

    public void decreaseFollowerCount() {
        this.followerCount--;
    }

    public void addProposers(List<Politician> representativeProposers, List<Politician> publicProposers) {
        List<Proposer> proposers = new ArrayList<>();
        for (Politician politician : representativeProposers) {
            Proposer proposer = Proposer.from(this, politician, ProposerRole.REPRESENTATIVE);
            politician.getProposeList().add(proposer);
            proposers.add(proposer);
        }
        for (Politician politician : publicProposers) {
            proposers.add(Proposer.from(this, politician, ProposerRole.PUBLIC));
            Proposer proposer = Proposer.from(this, politician, ProposerRole.REPRESENTATIVE);
            politician.getProposeList().add(proposer);
        }
        this.getProposers().addAll(proposers);

    }
}
