package com.luffyu.lock.redis;

import com.luffyu.lock.redis.setnx.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
class LockForRedisApplicationTests {


    @Resource
    private RedisLock redisLock;

    @Test
    void contextLoads() {

    }


    @Test
    void testUnSafe() throws InterruptedException {
        String test1 = "unsafeTestKey";
        if(!redisLock.tryAcquireLockUnSafe(test1)){
            log.info("获取锁失败");
        }
        try{
            log.info("执行业务");
            TimeUnit.SECONDS.sleep(1);
        }finally {
            redisLock.tryReleaseLockUnSafe(test1);
        }
    }



    @Test
    void testSafe() throws InterruptedException {
        String test1 = "safeTestKey";
        long sysTime= System.currentTimeMillis();
        if(!redisLock.tryAcquireLockSafe(test1,String.valueOf(sysTime))){
            log.info("获取锁失败");
        }
        try{
            log.info("执行业务");
            TimeUnit.SECONDS.sleep(1);
        }finally {
            redisLock.tryReleaseLockSafe(test1,String.valueOf(sysTime));
        }
    }

}
