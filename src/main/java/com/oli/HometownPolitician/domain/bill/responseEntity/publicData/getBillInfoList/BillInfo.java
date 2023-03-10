package com.oli.HometownPolitician.domain.bill.responseEntity.publicData.getBillInfoList;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BillInfo {
    private String billId;
    private String billName;
    private int billNo;
    private String passGubn;
    private String procStageCd;
    private LocalDate proposeDt;
    private String proposerKind;
    private String summary;
}
