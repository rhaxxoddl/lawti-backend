package com.oli.HometownPolitician.domain.committe.repository;

import com.oli.HometownPolitician.domain.committe.entity.Committee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitteeRepository extends JpaRepository<Committee, Long> {
}
