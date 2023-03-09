package com.oli.HometownPolitician.domain.politician.repository;

import com.oli.HometownPolitician.domain.politician.entity.Politician;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoliticianRepository extends JpaRepository<Politician, Long>, PoliticianRepositoryCustom {
}
