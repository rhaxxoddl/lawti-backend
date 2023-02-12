package com.oli.HometownPolitician.domain.UserTagRelation.repository;

import com.oli.HometownPolitician.domain.UserTagRelation.entity.UserTagRelation;
import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.user.entity.User;

import java.util.List;

public interface UserTagRelationRepositoryCustom {
    List<UserTagRelation> qFindFollowedTagByUuid(String uuid);

    void qFollowingTagByUuid(User user, List<Tag> tags);
}
