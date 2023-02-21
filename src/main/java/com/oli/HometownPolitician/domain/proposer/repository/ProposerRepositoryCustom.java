package com.oli.HometownPolitician.domain.proposer.repository;

import com.oli.HometownPolitician.domain.proposer.entity.Proposer;

import java.util.List;

public interface ProposerRepositoryCustom {
    List<Proposer> qFindByBillId(Long billId);
}
