package com.luffyu.lock.db.service;

import com.luffyu.lock.db.aqs.MethodLock;
import org.springframework.stereotype.Service;

/**
 * <p>${description}</p>
 *
 * @author luffyu
 * @date 2019-12-28 16:55
 **/
@Service
public class TestService {


    @MethodLock
    public void testAcquireLock(){
        System.out.println("获取到数据库的唯一主键锁");
    }

}
