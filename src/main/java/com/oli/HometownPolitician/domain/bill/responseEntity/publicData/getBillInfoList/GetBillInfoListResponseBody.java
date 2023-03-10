package com.oli.HometownPolitician.domain.bill.responseEntity.publicData.getBillInfoList;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetBillInfoListResponseBody {
    @JsonProperty("response")
    private GetBillInfoListResponse response;
}
