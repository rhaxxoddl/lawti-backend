package com.oli.HometownPolitician.domain.bill.dto;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.bill.enumeration.BillStageType;
import com.oli.HometownPolitician.domain.bill.enumeration.PlenaryResultType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class BillDetailDto {
    private Long id;
    private String title;
    private BillStageType currentStage;
    private String summary;
    private ProposersDto proposers;
    private LocalDate proposerDate;
    private CommitteeDto committee;
    private LocalDate committeeDate;
    private LocalDate noticeEndDate;
    private PlenaryResultType plenaryResult;
    private LocalDate plenaryProcessingDage;
    private String billPdfUri;
    private BillDto alternativeBill;

    static public BillDetailDto from(Bill bill) {
        return BillDetailDto.builder()
                .id(bill.getId())
                .title(bill.getTitle())
                .currentStage(bill.getCurrentStage())
                .summary(bill.getSummary())
                .proposers(ProposersDto.from(bill.getProposers()))
                .proposerDate(bill.getProposeDate())
                .committee(CommitteeDto.from(bill.getCommittee()))
                .committeeDate(bill.getCommitteeDate())
                .noticeEndDate(bill.getNoticeEndDate())
                .plenaryResult(bill.getPlenaryResult())
                .plenaryProcessingDage(bill.getPlenaryProcessingDate())
                .billPdfUri(bill.getBillPdfUri())
                .alternativeBill(BillDto.from(bill.getAlternativeBill()))
                .build();
    }
}
