package com.luffyu.lock.zookeeper.controller;

import com.luffyu.lock.zookeeper.service.CuratorZookeeperLock;
import com.luffyu.lock.zookeeper.service.ZookeeperLockService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/lock/zookeeper")
public class ZookeeperLockController {

    @Resource
    private ZookeeperLockService zookeeperLockService;

    @Resource
    private CuratorZookeeperLock curatorZookeeperLock;


    @GetMapping("/acquire/{path}")
    public Object acquire(@PathVariable("path") String path){
        return zookeeperLockService.acquireLock(path);
    }



    @GetMapping("/release/{path}")
    public Object release(@PathVariable("path") String path){
        return zookeeperLockService.releaseLock(path);
    }


    @GetMapping("/test/Process")
    public Object process(String path){
        for (int i=0;i<2;i++){
            curatorZookeeperLock.testProcessMutex(path);
        }
        return true;
    }

    @GetMapping("/test/Semaphore")
    public Object semaphore(String path){
        for (int i=0;i<2;i++){
            curatorZookeeperLock.testSemaphoreMutex(path);
        }
        return true;
    }

}
