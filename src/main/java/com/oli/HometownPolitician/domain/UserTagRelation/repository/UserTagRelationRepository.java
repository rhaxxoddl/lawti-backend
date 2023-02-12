package com.oli.HometownPolitician.domain.UserTagRelation.repository;

import com.oli.HometownPolitician.domain.UserTagRelation.entity.UserTagRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTagRelationRepository extends JpaRepository<UserTagRelation, Long>, UserTagRelationRepositoryCustom {
}
