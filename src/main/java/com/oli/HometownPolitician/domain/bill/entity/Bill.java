package com.oli.HometownPolitician.domain.bill.entity;


import com.oli.HometownPolitician.domain.bill.enumeration.BillStageType;
import com.oli.HometownPolitician.domain.bill.enumeration.PlenaryResultType;
import com.oli.HometownPolitician.domain.committe.entity.Committee;
import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
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
    private Long externalBillId;
    @Column(name = "number", nullable = false)
    private Long number;
    @Column(name = "national_assembly_age")
    private Long nationalAssemblyAge;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "propose_date", nullable = false)
    private LocalDate proposeDate;
    @Column(name = "summary", nullable = false)
    private String summary;
    @Column(name = "bill_pdf_url")
    private String billPdfUrl;
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

    @Column(name = "committee_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "committee_id")
    private Committee committee;

    @Column(name = "alternative_bill_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BILL_ID")
    private Bill alternativeBillId;
}
