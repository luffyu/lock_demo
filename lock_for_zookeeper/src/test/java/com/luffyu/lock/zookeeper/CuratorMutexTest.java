package com.luffyu.lock.zookeeper;

import com.luffyu.lock.zookeeper.service.CuratorZookeeperLock;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * <p>${description}</p>
 *
 * @author luffyu
 * @date 2019-12-31 10:16
 **/
@SpringBootTest
public class CuratorMutexTest {

    @Resource
    private CuratorZookeeperLock curatorZookeeperLock;

    /**
     * 测试可重入锁
     */
    @Test
    public void testProcessMutex(){


    }
}
