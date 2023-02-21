package com.oli.HometownPolitician.domain.billMessage.dto;

import com.oli.HometownPolitician.domain.tag.dto.TagDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BillMessageRoomDto {
    private TagDto tag;
    private String title;
    private Long numberOfUnreadMessages;
    private String latestMessageContent;
}
