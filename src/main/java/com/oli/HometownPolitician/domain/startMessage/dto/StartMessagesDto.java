package com.oli.HometownPolitician.domain.startMessage.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StartMessagesDto {
    private List<StartMessageDto> list;

    static public StartMessagesDto from(List<StartMessageDto> startMessageDtoList) {
        return StartMessagesDto
                .builder()
                .list(startMessageDtoList)
                .build();
    }
}
