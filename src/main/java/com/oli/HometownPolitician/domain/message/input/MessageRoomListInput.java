package com.oli.HometownPolitician.domain.message.input;

import com.oli.HometownPolitician.global.argument.input.TargetSlicePaginationInput;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class MessageRoomListInput {
    @NotNull(message = "메세지 방들을 읽어오기 위한 값들이 입력되지 않았습니다.")
    private TargetSlicePaginationInput pagination;

    static public MessageRoomListInput from(Long target, Integer elementSize, Boolean isAscending) {
        return MessageRoomListInput.builder()
                .pagination(
                        TargetSlicePaginationInput
                                .from(target, elementSize, isAscending)
                )
                .build();
    }
}
