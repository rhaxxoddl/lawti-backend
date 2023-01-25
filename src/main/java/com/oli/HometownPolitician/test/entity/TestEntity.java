package com.oli.HometownPolitician.test.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@RedisHash("test-entity")
public class TestEntity {

    @Id
    private Long testEntityId;

}
