package com.oli.HometownPolitician.domain.user.dto;

import com.oli.HometownPolitician.domain.tag.entity.Tag;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TagDto {
    private Long id;
    private String name;

    public static TagDto from(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}
