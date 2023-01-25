package com.oli.HometownPolitician.test.repository;

import com.oli.HometownPolitician.test.entity.TestEntity;
import org.springframework.data.repository.CrudRepository;

public interface TestEntityRepository extends CrudRepository<TestEntity, Long> {
}
