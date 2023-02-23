package com.oli.HometownPolitician.domain.billMessage.repository;

import com.oli.HometownPolitician.domain.billMessage.entity.BillMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillMessageRepository extends JpaRepository<BillMessage, Long>, BillMessageRepositoryCustom {
}
