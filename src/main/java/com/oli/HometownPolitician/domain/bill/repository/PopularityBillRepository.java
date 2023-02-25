package com.oli.HometownPolitician.domain.bill.repository;

import com.oli.HometownPolitician.domain.bill.entity.PopularityBill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopularityBillRepository extends JpaRepository<PopularityBill, Long>, PopularityBillRepositoryCustom {
}
