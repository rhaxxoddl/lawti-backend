package com.oli.HometownPolitician.global.argument.output;

import com.oli.HometownPolitician.global.argument.input.TargetSlicePaginationInput;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SlicePaginationDto {

    private Integer returnedElementSize;

    private Integer requestedElementSize;

    private Boolean isFirst;

    private Boolean isLast;

    private Boolean isEmpty;

    private Boolean isAscending;

    static public SlicePaginationDto from(TargetSlicePaginationInput input, Integer size) {
        if (input == null || size == null) {
            return null;
        }
        return SlicePaginationDto
                .builder()
                .returnedElementSize(size)
                .requestedElementSize(input.getElementSize())
                .isFirst(input.getTarget() == null)
                .isLast(size < input.getElementSize())
                .isEmpty(size == 0)
                .isAscending(input.getIsAscending())
                .build();
    }

}
