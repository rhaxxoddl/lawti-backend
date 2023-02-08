package com.oli.HometownPolitician.domain.startMessage.repository;

import com.oli.HometownPolitician.domain.startMessage.entity.StartMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StartMessageRepository extends JpaRepository<StartMessage, Long> {
}
