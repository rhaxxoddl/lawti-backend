package com.oli.HometownPolitician.domain.committee.responseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oli.HometownPolitician.global.responseEntity.Head;
import lombok.Data;

@Data
public class CurrentCommittees {
    private Head[] head;
    @JsonProperty("row")
    private CommitteeInfo[] committeeInfos;
}
