package com.oli.HometownPolitician.domain.committee.repository;

import com.oli.HometownPolitician.domain.committee.entity.Committee;

import java.util.List;
import java.util.Optional;

public interface CommitteeRepositoryCustom {
    List<Committee> qFindAll();
    Optional<Committee> qFindByExternalCommitteeId(String externalCommitteeId);
}
