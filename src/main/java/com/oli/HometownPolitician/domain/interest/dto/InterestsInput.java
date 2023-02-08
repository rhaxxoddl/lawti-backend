package com.oli.HometownPolitician.domain.interest.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Builder
public class InterestsInput {
    @NotEmpty
    private List<InterestInput> list;
}
