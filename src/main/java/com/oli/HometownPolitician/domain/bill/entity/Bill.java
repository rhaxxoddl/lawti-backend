package com.oli.HometownPolitician.domain.bill.entity;


import com.oli.HometownPolitician.domain.bill.enumeration.BillStageType;
import com.oli.HometownPolitician.domain.bill.enumeration.PlenaryResultType;
import com.oli.HometownPolitician.domain.billUserRelation.entity.BillUserRelation;
import com.oli.HometownPolitician.domain.committe.entity.Committee;
import com.oli.HometownPolitician.domain.billMessage.entity.BillMessage;
import com.oli.HometownPolitician.domain.proposer.entity.Proposer;
import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "bills")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "BILL_SEQ_GENERATOR", sequenceName = "BILL_SEQ")
public class Bill extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BILL_SEQ")
    @Column(name = "bill_id", unique = true, nullable = false)
    private Long id;
    @Column(name = "external_bill_id", unique = true, nullable = false)
    private String externalBillId;
    @Column(name = "number", nullable = false)
    private Long number;
    @Column(name = "title", nullable = false)
    private String title;
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

    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY)
    private List<BillUserRelation> followedBillUserRelations = new ArrayList<>();

    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY)
    private List<BillMessage> billMessages = new ArrayList<>();
}
