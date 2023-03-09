package com.oli.HometownPolitician.domain.politician.repository;

import com.oli.HometownPolitician.domain.politician.entity.Politician;

public interface PoliticianRepositoryCustom {
    Politician queryPoliticiansByPolitician(Politician input);
}
