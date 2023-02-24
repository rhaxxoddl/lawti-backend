package com.oli.HometownPolitician.domain.billUserRelation.repository;

import com.oli.HometownPolitician.domain.billUserRelation.entity.BillUserRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillUserRelationRepository extends JpaRepository<BillUserRelation, Long>, BillUserRelationRepositoryCustom {
}
