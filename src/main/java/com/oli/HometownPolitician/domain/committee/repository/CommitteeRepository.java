package com.oli.HometownPolitician.domain.committee.repository;

import com.oli.HometownPolitician.domain.committee.entity.Committee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitteeRepository extends JpaRepository<Committee, Long> {
}
