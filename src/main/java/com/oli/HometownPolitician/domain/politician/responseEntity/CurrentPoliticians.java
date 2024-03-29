package com.oli.HometownPolitician.domain.politician.responseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oli.HometownPolitician.global.responseEntity.Head;
import lombok.Data;

@Data
public class CurrentPoliticians {
    private Head[] head;
    @JsonProperty("row")
    private PoliticianInfo[] politicianInfos;
}
