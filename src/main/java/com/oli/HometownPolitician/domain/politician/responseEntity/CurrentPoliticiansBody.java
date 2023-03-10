package com.oli.HometownPolitician.domain.politician.responseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrentPoliticiansBody {
    @JsonProperty("nprlapfmaufmqytet")
    private CurrentPoliticians[] currentPoliticiansList;
}
