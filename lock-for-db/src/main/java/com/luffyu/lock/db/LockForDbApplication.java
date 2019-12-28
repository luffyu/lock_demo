package com.luffyu.lock.db;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.luffyu.lock.db.**")
@ComponentScan("com.luffyu.lock.db.**")
public class LockForDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(LockForDbApplication.class, args);
    }

}
