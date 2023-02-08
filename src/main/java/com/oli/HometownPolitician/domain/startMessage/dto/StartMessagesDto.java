package com.oli.HometownPolitician.domain.startMessage.dto;

import com.oli.HometownPolitician.domain.startMessage.entity.StartMessage;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class StartMessagesDto {
    private List<StartMessageDto> list;

    static public StartMessagesDto from(List<StartMessage> startMessageList) {
        return StartMessagesDto
                .builder()
                .list(startMessageList
                        .stream()
                        .map(StartMessageDto::from)
                        .collect(Collectors.toList())
                )
                .build();
    }
}
