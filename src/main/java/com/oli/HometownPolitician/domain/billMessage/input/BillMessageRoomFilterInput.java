package com.oli.HometownPolitician.domain.billMessage.input;

import com.oli.HometownPolitician.domain.tag.dto.TagInput;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
public class BillMessageRoomFilterInput {
    @NotNull(message = "tagList를 NULL로 둘 수 없습니다")
    private List<TagInput> tagList;
}
