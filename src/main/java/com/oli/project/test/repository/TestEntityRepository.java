package com.oli.project.test.repository;

import com.oli.project.test.entity.TestEntity;
import org.springframework.data.repository.CrudRepository;

public interface TestEntityRepository extends CrudRepository<TestEntity, Long> {
}
