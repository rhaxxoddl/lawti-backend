package com.oli.HometownPolitician.domain.Lawti.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StartMessagesDto {
    private List<StartMessageDto> startMessageDtoList;

    static public StartMessagesDto from(List<StartMessageDto> startMessageDtoList) {
        return StartMessagesDto
                .builder()
                .startMessageDtoList(startMessageDtoList)
                .build();
    }
}
