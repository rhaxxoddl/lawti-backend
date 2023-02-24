package com.oli.HometownPolitician.domain.billMessage.dto;

import com.oli.HometownPolitician.domain.billMessage.entity.BillMessage;
import com.oli.HometownPolitician.domain.billTagRelation.entity.BillTagRelation;
import com.oli.HometownPolitician.domain.billUserRelation.entity.BillUserRelation;
import com.oli.HometownPolitician.domain.tag.dto.TagDto;
import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.global.tool.ListTool;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BillMessageRoomDto {
    private Long billId;
    private TagDto tag;
    private String title;
    private int numberOfUnreadMessages;
    private String latestMessageContent;

    static public BillMessageRoomDto from(BillUserRelation billUserRelation) {
        return BillMessageRoomDto.builder()
                .billId(billUserRelation.getBill().getId())
                .tag(getRepresentativeTag(billUserRelation))
                .title(billUserRelation.getBill().getTitle())
                .numberOfUnreadMessages(countUnreadMessages(billUserRelation))
                .latestMessageContent(getLatestMessageContent(billUserRelation))
                .build();
    }

    static private int countUnreadMessages(BillUserRelation billUserRelation) {
        List<BillMessage> billMessages = billUserRelation.getBill().getBillMessages();
        BillMessage lastReadBillMessage = billUserRelation.getLastReadBillMessage();

        int lastReadBillMessageIndex = billMessages.indexOf(lastReadBillMessage);
        if (lastReadBillMessageIndex == -1)
            return billMessages.size();
        return billMessages.size() - lastReadBillMessageIndex - 1;
    }

    static private String getLatestMessageContent(BillUserRelation billUserRelation) {
        return ListTool.getLastElement(
                billUserRelation.getBill().getBillMessages()
        ).getContent();
    }

    // TODO 아직 대표태그가 아니라 첫번째 태그만 보냄. 추후에 대표태그만 보내는 로직 짜기
    static private TagDto getRepresentativeTag(BillUserRelation billUserRelation) {
        List<Tag> tags = billUserRelation.getBill().getTags()
                .stream()
                .map(BillTagRelation::getTag)
                .toList();
        return tags.size() > 0 ? TagDto.from(tags.get(0)) : null;
    }
}
