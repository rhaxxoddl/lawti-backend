package com.oli.HometownPolitician.domain.billMessage.dto;

import com.oli.HometownPolitician.domain.billMessage.entity.BillMessage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageDto {
    private Long messageId;
    private String messageContent;
    private boolean isRead;

    static public MessageDto from(BillMessage billMessage, boolean isRead) {
        return MessageDto.builder()
                .messageId(billMessage.getId())
                .messageContent(billMessage.getContent())
                .isRead(isRead)
                .build();
    }
}
