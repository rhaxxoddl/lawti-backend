package com.oli.HometownPolitician.domain.user.dto;

import com.oli.HometownPolitician.domain.tag.entity.Tag;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class TagsDto {
    private List<TagDto> list;

    public static TagsDto from(List<Tag> tagList) {
        return TagsDto.builder()
                .list(
                        tagList.stream()
                                .map(TagDto::from)
                                .collect(Collectors.toList()))
                .build();
    }
}
