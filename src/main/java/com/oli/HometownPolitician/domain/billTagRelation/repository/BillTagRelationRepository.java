package com.oli.HometownPolitician.domain.billTagRelation.repository;

import com.oli.HometownPolitician.domain.billTagRelation.entity.BillTagRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillTagRelationRepository extends JpaRepository<BillTagRelation, Long> {
}
