package com.luffyu.lock.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LockForRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(LockForRedisApplication.class, args);
    }

}
