package com.luffyu.lock.db;

import com.luffyu.lock.db.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * <p>${description}</p>
 *
 * @author luffyu
 * @date 2019-12-28 16:56
 **/
@SpringBootTest
public class AcquireLockTest {


    @Resource
    private TestService testService;



    @Test
    public void testMethod(){
        testService.testAcquireLock();
    }

}
