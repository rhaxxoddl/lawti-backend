package com.oli.HometownPolitician.domain.userTagRelation.repository;

import com.oli.HometownPolitician.domain.userTagRelation.entity.UserTagRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTagRelationRepository extends JpaRepository<UserTagRelation, Long>, UserTagRelationRepositoryCustom {
}
