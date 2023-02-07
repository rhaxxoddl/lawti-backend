package com.oli.HometownPolitician.domain.Lawti.dto;

import com.oli.HometownPolitician.domain.Lawti.entity.StartMessage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StartMessageDto {
    private Long id;
    private String greetings;

    static public StartMessageDto from(StartMessage startMessage) {
        return StartMessageDto
                .builder()
                .id(startMessage.getId())
                .greetings(startMessage.getMessage())
                .build();
    }
}
