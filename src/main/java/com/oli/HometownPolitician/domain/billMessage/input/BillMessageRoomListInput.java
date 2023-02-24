package com.oli.HometownPolitician.domain.billMessage.input;

import com.oli.HometownPolitician.global.argument.input.TargetSlicePaginationInput;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class BillMessageRoomListInput {

    @NotNull(message = "filter를 NULL로 둘 수 없습니다.")
    private BillMessageRoomFilterInput filter;
    @NotNull(message = "메세지 방들을 읽어오기 위한 값들이 입력되지 않았습니다")
    private TargetSlicePaginationInput pagination;
}
