package com.oli.HometownPolitician.domain.interest.repository;

import com.oli.HometownPolitician.domain.interest.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestRepository extends JpaRepository<Interest, Long> {
}
