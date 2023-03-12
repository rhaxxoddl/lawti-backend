package com.oli.HometownPolitician.domain.bill.responseEntity.openAssembly.searchBills;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SearchBillsBody {
    @JsonProperty("TVBPMBILL11")
    private SearchBills searchBill;
}
