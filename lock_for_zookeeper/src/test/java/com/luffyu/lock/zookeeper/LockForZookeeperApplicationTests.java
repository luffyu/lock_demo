package com.luffyu.lock.zookeeper;

import com.luffyu.lock.zookeeper.service.ZookeeperLockService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@SpringBootTest
class LockForZookeeperApplicationTests {

    @Resource
    ZookeeperLockService zookeeperLockService;

    @Test
    void contextLoads() {

        String lock = "test";



        zookeeperLockService.acquireLock(lock);

        zookeeperLockService.acquireLock(lock);

        zookeeperLockService.releaseLock(lock);

    }

}
