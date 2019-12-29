package com.luffyu.lock.zookeeper.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * <p>${description}</p>
 *
 * @author luffyu
 * @date 2019-12-29 11:53
 **/
@Slf4j
@Service
public class ZookeeperLockService {

    private final static String ROOT_PATH_LOCK = "/rootlock/";

    @Resource
    private CuratorFramework curatorFramework;



    /**
     * 获取分布式锁
     */
    public boolean acquireLock(String path) {
        String keyPath =  ROOT_PATH_LOCK + path;
        try {
            curatorFramework
                    .create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath(keyPath);
            log.info("success to acquire lock for path:{}", keyPath);
            return true;
        } catch (Exception e) {
            log.info("failed to acquire lock for path:{}", keyPath);
            return false;
        }
    }


    /**
     * 释放分布式锁
     */
    public boolean releaseLock(String path) {
        try {
            String keyPath =  ROOT_PATH_LOCK +  path;
            if (curatorFramework.checkExists().forPath(keyPath) != null) {
                curatorFramework.delete().forPath(keyPath);
            }
        } catch (Exception e) {
            log.error("failed to release lock");
            return false;
        }
        return true;
    }

}
