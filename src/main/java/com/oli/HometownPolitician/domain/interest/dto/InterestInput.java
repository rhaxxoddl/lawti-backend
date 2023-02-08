package com.oli.HometownPolitician.domain.interest.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class InterestInput {
    @NotNull(message = "Interest의 id는 빈값일 수 없습니다.")
    @Min(value = 1, message = "Interest의 id는 1보다 작을 수 없습니다.")
    private Long id;

    @NotBlank(message = "Interest의 name은 빈값일 수 없습니다.")
    private String name;
}
