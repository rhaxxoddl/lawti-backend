package com.oli.HometownPolitician.domain.bill.responseEntity.publicData.getBillInfoList;

import lombok.Data;

@Data
public class GetBillInfoListBody {
    private BillInfos items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}
