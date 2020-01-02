package com.luffyu.lock.zookeeper.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;
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

    private final static String ROOT_PATH_LOCK = "/rootLock";

    @Resource
    private CuratorFramework curatorFramework;



    /**
     * 获取分布式锁
     */
    public boolean acquireLock(String path) {
        String keyPath =  ROOT_PATH_LOCK + "/" +path;
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
            String keyPath =  ROOT_PATH_LOCK + "/" + path;
            if (curatorFramework.checkExists().forPath(keyPath) != null) {
                curatorFramework.delete().forPath(keyPath);
            }
        } catch (Exception e) {
            log.error("failed to release lock");
            return false;
        }
        return true;
    }


    /**
     * 创建 watcher 事件
     */
    private void addWatcher(String path) throws Exception {
        String keyPath;
        if (path.equals(ROOT_PATH_LOCK)) {
            keyPath = path;
        } else {
            keyPath =  ROOT_PATH_LOCK + "/" + path;
        }
        final PathChildrenCache cache = new PathChildrenCache(curatorFramework, keyPath, false);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener((client, event) -> {
            if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                String oldPath = event.getData().getPath();
                log.info("上一个节点 "+ oldPath + " 已经被断开");
                if (oldPath.contains(path)) {
                    //释放计数器，让当前的请求获取锁
                    //countDownLatch.countDown();
                }
            }
        });
    }

    //创建父节点，并创建永久节点˚
    public void afterPropertiesSet() {
        curatorFramework = curatorFramework.usingNamespace("lock-namespace");
        String path = ROOT_PATH_LOCK;
        try {
            if (curatorFramework.checkExists().forPath(path) == null) {
                curatorFramework.create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath(path);
            }
            addWatcher(ROOT_PATH_LOCK);
            log.info("root path 的 watcher 事件创建成功");
        } catch (Exception e) {
            log.error("connect zookeeper fail，please check the log >> {}", e.getMessage(), e);
        }
    }
}
