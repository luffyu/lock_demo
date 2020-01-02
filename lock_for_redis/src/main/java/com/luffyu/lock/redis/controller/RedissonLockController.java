package com.luffyu.lock.redis.controller;

import com.luffyu.lock.redis.redisson.RedissonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>${description}</p>
 *
 * @author luffyu
 * @date 2019-12-29 12:00
 **/
@RestController
@RequestMapping("/lock/redisson")
public class RedissonLockController {

    @Resource
    private RedissonService redissonService;



    @GetMapping("/acquire/{path}")
    public Object acquire(@PathVariable("path") String path){
        redissonService.acquire(path);
        return true;
    }




}
