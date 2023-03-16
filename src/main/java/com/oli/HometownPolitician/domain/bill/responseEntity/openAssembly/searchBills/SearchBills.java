package com.oli.HometownPolitician.domain.bill.responseEntity.openAssembly.searchBills;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oli.HometownPolitician.global.responseEntity.Head;
import lombok.Data;

@Data
public class SearchBills {
    private Head[] head;
    @JsonProperty("row")
    private SearchBill[] SearchBills;
}
