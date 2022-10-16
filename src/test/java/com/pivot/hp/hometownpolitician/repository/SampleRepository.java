package com.pivot.hp.hometownpolitician.repository;

import com.pivot.hp.hometownpolitician.entity.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<Sample, Long> {
}

