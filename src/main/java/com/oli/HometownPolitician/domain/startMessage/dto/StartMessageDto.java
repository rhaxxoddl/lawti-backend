package com.oli.HometownPolitician.domain.startMessage.dto;

import com.oli.HometownPolitician.domain.startMessage.entity.StartMessage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StartMessageDto {
    private Long id;
    private String message;

    static public StartMessageDto from(StartMessage startMessage) {
        return StartMessageDto
                .builder()
                .id(startMessage.getId())
                .message(startMessage.getMessage())
                .build();
    }
}
