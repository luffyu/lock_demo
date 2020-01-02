package com.luffyu.lock.zookeeper.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <p>${description}</p>
 *
 * @author luffyu
 * @date 2019-12-31 10:08
 **/
@Slf4j
@Service
public class CuratorZookeeperLock {



    @Resource
    private CuratorFramework curatorFramework;


    /**
     * 创建可重入锁
     * @param lockPath
     */
    public void testProcessMutex(String lockPath){
        //定义一个可重入锁  在创建锁对象的时候 阻塞了
        InterProcessLock lock=new InterProcessMutex(curatorFramework,lockPath);
        String name = Thread.currentThread().getName();
        try {
            log.info("线程{}开始尝试获取可重入锁",name);
            lock.acquire();
            runTask();
        } catch (Throwable e) {
            e.printStackTrace();
        }finally {
            try {
                //释放锁
                lock.release();
                log.info("线程{}释放可重入锁成功",name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 创建不可重入锁
     * @param lockPath
     */
    public void testSemaphoreMutex(String lockPath){
        //定义一个可重入锁  在创建锁对象的时候 阻塞了
        InterProcessLock lock=new InterProcessSemaphoreMutex(curatorFramework,lockPath);
        String name = Thread.currentThread().getName();
        try {
            log.info("线程{}开始尝试获取不可重入锁",name);
            lock.acquire();
            runTask();
        } catch (Throwable e) {
            e.printStackTrace();
        }finally {
            try {
                //释放锁
                lock.release();
                log.info("线程{}释放不可重入锁成功",name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 创建重入读写锁
     * @param lockPath
     */
    public void testReadWriteLock(String lockPath){
        //定义一个可重入锁  在创建锁对象的时候 阻塞了
        InterProcessReadWriteLock lock=new InterProcessReadWriteLock(curatorFramework,lockPath);
        String name = Thread.currentThread().getName();
        try {
            log.info("线程{}开始尝试获取锁",name);
            lock.readLock();
            runTask();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                //释放锁
                log.info("线程{}释放锁成功",name);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
