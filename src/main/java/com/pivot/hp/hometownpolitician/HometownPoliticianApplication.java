package com.pivot.hp.hometownpolitician;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class HometownPoliticianApplication {

    public static void main(String[] args) {
        SpringApplication.run(HometownPoliticianApplication.class, args);
    }

}
