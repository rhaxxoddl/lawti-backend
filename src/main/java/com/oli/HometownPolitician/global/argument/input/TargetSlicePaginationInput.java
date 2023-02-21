package com.oli.HometownPolitician.global.argument.input;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class TargetSlicePaginationInput {

    private Long target;

    @NotNull(message = "읽고 싶은 크기가 기재되지 않았습니다.")
    @Min(value = 1, message = "읽고 싶은 크기는 1 이상인 값이어야 합니다.")
    private Integer elementSize;

    @NotNull(message = "오름차순 여부가 기재되지 않았습니다.")
    private Boolean isAscending;

    static public TargetSlicePaginationInput from(Long target, Integer elementSize, Boolean isAscending) {
        return TargetSlicePaginationInput
                .builder()
                .target(target)
                .elementSize(elementSize)
                .isAscending(isAscending)
                .build();
    }

}
