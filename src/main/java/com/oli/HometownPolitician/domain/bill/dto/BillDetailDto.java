package com.oli.HometownPolitician.domain.bill.dto;

import com.oli.HometownPolitician.domain.bill.enumeration.BillStageType;
import com.oli.HometownPolitician.domain.bill.enumeration.PlenaryResultType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class BillDetailDto {
    private Long id;
    private String title;
    private BillStageType currentStage;
    private String summary;
    private List<PoliticianDto> proposers;
    private LocalDate proposerDate;
    private CommitteeDto committe;
    private LocalDate noticeEndDate;
    private LocalDate committeDate;
    private PlenaryResultType plenaryResult;
    private LocalDate plenaryProcessingDage;
    private String billPdfUri;
    private BillDto alternativeBillId;
}
