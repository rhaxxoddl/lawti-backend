package com.oli.HometownPolitician.domain.bill.responseEntity.publicData.getBillInfoList;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BillInfo {
    private String billId;
    private String billName;
    private int billNo;
    private String passGubn;
    private String procStageCd;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate proposeDt;
    private String proposerKind;
    private String summary;
}
