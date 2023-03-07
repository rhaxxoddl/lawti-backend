package com.oli.HometownPolitician.domain.committee.repository;

import com.oli.HometownPolitician.domain.committee.entity.Committee;

import java.util.List;

public interface CommitteeRepositoryCustom {
    List<Committee> qFindAll();
}
