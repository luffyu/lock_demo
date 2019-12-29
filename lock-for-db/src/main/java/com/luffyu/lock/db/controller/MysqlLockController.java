package com.luffyu.lock.db.controller;

import com.luffyu.lock.db.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>${description}</p>
 *
 * @author luffyu
 * @date 2019-12-29 12:05
 **/
@RestController
@RequestMapping("/lock/mysql")
public class MysqlLockController {


    @Resource
    private TestService testService;



    @GetMapping("/acquire")
    public Object acquire(){
        testService.testAcquireLock();
        return true;
    }



}
