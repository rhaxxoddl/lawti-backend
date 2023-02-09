package com.oli.HometownPolitician.domain.tag.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Builder
public class TagsInput {
    @NotEmpty
    private List<TagInput> list;
}
