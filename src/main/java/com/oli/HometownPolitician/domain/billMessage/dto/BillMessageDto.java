package com.oli.HometownPolitician.domain.billMessage.dto;

import com.oli.HometownPolitician.domain.billMessage.entity.BillMessage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BillMessageDto {
    private Long billMessageId;
    private String content;

    static public BillMessageDto from(BillMessage billMessage) {
        return BillMessageDto.builder()
                .billMessageId(billMessage.getId())
                .content(billMessage.getContent())
                .build();
    }
}
