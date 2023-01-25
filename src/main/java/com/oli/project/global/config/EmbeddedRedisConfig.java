package com.oli.project.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Profile({"local", "test"})
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.data.redis.port}")
    private Integer redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void startServer() throws IOException {
        redisServer = RedisServer.builder().port(redisPort).setting("maxmemory 128M").build();
        try {
            redisServer.start();
        } catch (Exception e) {
        }
    }

    @PreDestroy
    public void stopServer() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

}
