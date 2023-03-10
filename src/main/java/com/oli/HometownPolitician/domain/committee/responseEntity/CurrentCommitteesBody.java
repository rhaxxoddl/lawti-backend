package com.oli.HometownPolitician.domain.committee.responseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrentCommitteesBody {
    @JsonProperty("nxrvzonlafugpqjuh")
    private CurrentCommittees[] currentCommitteesList;
}
