package com.luffyu.lock.redis;

import com.luffyu.lock.redis.redisson.RedissonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>${description}</p>
 *
 * @author luffyu
 * @date 2019-12-30 20:12
 **/

@Slf4j
@SpringBootTest
public class RedissonTest {


    @Autowired
    private RedissonService redissonService;



    @Test
    public void test(){

        redissonService.acquire("tett");
    }


}
