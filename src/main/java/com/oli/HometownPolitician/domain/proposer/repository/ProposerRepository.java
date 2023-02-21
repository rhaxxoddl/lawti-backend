package com.oli.HometownPolitician.domain.proposer.repository;

import com.oli.HometownPolitician.domain.proposer.entity.Proposer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProposerRepository extends JpaRepository<Proposer, Long>, ProposerRepositoryCustom {
}
