package com.luffyu.lock.db;

import com.luffyu.lock.db.service.AcquireLockService;
import com.luffyu.lock.db.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * <p>悲观锁的测试</p>
 *
 * @author luffyu
 * @date 2019-12-29 11:08
 **/
@SpringBootTest
public class PessimisticLockTest {

    @Resource
    private AcquireLockService acquireLockService;



    @Test
    public void testMethod(){
        acquireLockService.updateForUpdate(1);
    }


}
