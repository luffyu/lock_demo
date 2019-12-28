package com.luffyu.lock.db.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luffyu.lock.db.entity.AcquireLockEntity;
import com.luffyu.lock.db.mapper.AcquireLockMapper;
import org.springframework.stereotype.Service;


/**
 * <p>${description}</p>
 *
 * @author luffyu
 * @date 2019-12-28 16:24
 **/
@Service
public class AcquireLockService extends ServiceImpl<AcquireLockMapper,AcquireLockEntity> {



    /**
     * 获取锁资源
     * @param methodName 方法名称
     * @return
     */
    public boolean tryAcquire(String methodName){
        try{
            if(findByMethodName(methodName) != null){
                return false;
            }
            AcquireLockEntity acquireLockEntity = new AcquireLockEntity(methodName);
            return save(acquireLockEntity);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 释放锁资源
     * @param methodName 方法名称
     * @return 如果没有删除成功怎么办？？？
     */
    public boolean tyrRelease(String methodName){
        QueryWrapper<AcquireLockEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("method_name",methodName);
        return remove(queryWrapper);
    }


    /**
     * 通过方法名称获取对象
     * @param methodName 方法名称
     * @return 返回锁
     */
    private AcquireLockEntity findByMethodName(String methodName){
        QueryWrapper<AcquireLockEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("method_name",methodName);
        return getOne(queryWrapper);
    }

}
