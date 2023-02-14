package com.oli.HometownPolitician.domain.userTagRelation.repository;

import com.oli.HometownPolitician.domain.userTagRelation.entity.UserTagRelation;

import java.util.List;

public interface UserTagRelationRepositoryCustom {
    List<UserTagRelation> qFindFollowedTagsByUuid(String uuid);
}
