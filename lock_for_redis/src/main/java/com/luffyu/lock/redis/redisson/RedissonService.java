package com.luffyu.lock.redis.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <p>${description}</p>
 *
 * @author luffyu
 * @date 2019-12-30 18:58
 **/
@Slf4j
@Service
public class RedissonService {


    @Autowired
    private RedissonClient redissonClient;



    public void acquire(String key){
        RLock lock = redissonClient.getLock(key);
        //获取锁的时候阻塞
        lock.lock();
        try{
            runTask();
        }finally {
            lock.unlock();
        }
    }



    private void runTask(){
        try {
            String name = Thread.currentThread().getName();
            log.info("线程{}获取到锁>>>>>",name);
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
