package com.oli.HometownPolitician.domain.politician.repository;

import com.oli.HometownPolitician.domain.politician.entity.Politician;

import java.util.Optional;

public interface PoliticianRepositoryCustom {
    Optional<Politician> queryPoliticiansByPolitician(Politician input);
}
