package com.oli.HometownPolitician.domain.user.repository;

import com.oli.HometownPolitician.domain.user.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> qFindByUuidWithTag(String userUuid);
}
