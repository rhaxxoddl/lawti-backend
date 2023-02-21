package com.oli.HometownPolitician.domain.billMessage.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageDto {
    private Long messageId;
    private String messageContent;
    private boolean isRead;
}
