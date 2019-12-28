package com.luffyu.lock.db.aqs;

import com.luffyu.lock.db.service.AcquireLockService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;


/**
 * @author luffyu
 * Created on 2019-03-22
 */
@Slf4j
@Aspect
@Component
public class MethodLockAspect {


    @Resource
    private AcquireLockService acquireLockService;



    @Pointcut("@annotation(com.luffyu.lock.db.aqs.MethodLock)")
    public void getPointCut(){ }


    @Around(value = "getPointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Signature s = proceedingJoinPoint.getSignature();
        MethodSignature ms = (MethodSignature)s;
        Method m = ms.getMethod();
        String methodName = m.getDeclaringClass().getName()+"."+m.getName();
        System.out.println("开始获取"+methodName+"的锁");
        //获取当前的方法名称
        if(!acquireLockService.tryAcquire(methodName)){
            throw new RuntimeException("No lock was acquired");
        }
        System.out.println("获取"+methodName+"的锁成功");
        try{
            return proceedingJoinPoint.proceed();
        }finally {
            acquireLockService.tyrRelease(methodName);
        }
    }

}
