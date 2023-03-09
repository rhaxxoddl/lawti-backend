package com.oli.HometownPolitician.domain.politician.responseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrentStatusOfPoliticiansBody {
    @JsonProperty("nprlapfmaufmqytet")
    private CurrentStatusOfPoliticians[] currentStatusOfPoliticiansList;
}
