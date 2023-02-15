package com.oli.HometownPolitician.domain.tag.dto;


import com.oli.HometownPolitician.domain.tag.entity.Tag;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TagDto {
    private Long id;
    private String name;

    static public TagDto from(Tag tag) {
        return TagDto
                .builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}
