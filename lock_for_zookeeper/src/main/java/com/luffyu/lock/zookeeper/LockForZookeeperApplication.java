package com.luffyu.lock.zookeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.luffyu.lock.zookeeper.**")
public class LockForZookeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(LockForZookeeperApplication.class, args);
    }

}
