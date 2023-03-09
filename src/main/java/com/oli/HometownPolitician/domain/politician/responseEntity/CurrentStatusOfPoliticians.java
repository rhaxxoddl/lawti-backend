package com.oli.HometownPolitician.domain.politician.responseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrentStatusOfPoliticians {
    private Head[] head;
    @JsonProperty("row")
    private PoliticianInfo[] politicians;
}
