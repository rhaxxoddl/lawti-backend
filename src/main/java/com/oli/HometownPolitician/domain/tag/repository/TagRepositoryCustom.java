package com.oli.HometownPolitician.domain.tag.repository;

import com.oli.HometownPolitician.domain.tag.entity.Tag;

import java.util.List;

public interface TagRepositoryCustom {
    List<Tag> queryTagsByNameList(List<String> nameList);
}
