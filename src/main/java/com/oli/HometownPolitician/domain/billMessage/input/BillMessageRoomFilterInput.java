package com.oli.HometownPolitician.domain.billMessage.input;

import com.oli.HometownPolitician.domain.tag.dto.TagInput;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BillMessageRoomFilterInput {
    private List<TagInput> tagList;
}
