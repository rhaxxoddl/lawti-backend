package com.oli.HometownPolitician.domain.committee.input;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class CommitteeInput {
    @NotNull(message = "committeeId로 NULL이 들어올 수 없습니다")
    private Long committeeId;
}
