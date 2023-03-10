package com.oli.HometownPolitician.domain.bill.responseEntity.publicData.getBillInfoList;

import com.oli.HometownPolitician.global.responseEntity.xml.Header;
import lombok.Data;

@Data
public class GetBillInfoListResponse {
    private Header header;
    private GetBillInfoListBody body;
}
