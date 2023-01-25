package com.oli.project.test.domain;

import com.oli.project.test.entity.TestEntity;
import com.oli.project.test.repository.TestEntityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class TestEntityRedisTest {

    @Autowired
    private TestEntityRepository testEntityRepository;

    private TestEntity testEntity;

    @BeforeEach
    void before() {
        testEntity = TestEntity.builder().testEntityId(1L).build();
    }

    @AfterEach
    void after() {
        testEntityRepository.deleteById(testEntity.getTestEntityId());
    }

    @Test
    void redis_save_test() {
        testEntityRepository.save(testEntity);
        TestEntity savedTestEntity = testEntityRepository.findById(testEntity.getTestEntityId())
                .orElseThrow(RuntimeException::new);
        assertThat(savedTestEntity.getTestEntityId()).isEqualTo(testEntity.getTestEntityId());
    }

    @Test
    void redis_delete_test() {
        testEntityRepository.save(testEntity);
        testEntityRepository.delete(testEntity);
        Optional<TestEntity> deletedTestEntity = testEntityRepository.findById(testEntity.getTestEntityId());
        assertTrue(deletedTestEntity.isEmpty());
    }

}
