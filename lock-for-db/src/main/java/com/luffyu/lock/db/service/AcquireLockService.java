package com.luffyu.lock.db.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luffyu.lock.db.entity.AcquireLockEntity;
import com.luffyu.lock.db.mapper.AcquireLockMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * <p>${description}</p>
 *
 * @author luffyu
 * @date 2019-12-28 16:24
 **/
@Service
public class AcquireLockService extends ServiceImpl<AcquireLockMapper,AcquireLockEntity> {



    /*********************************************唯一主键***********************************************************************
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


    /**
     * *********************************************悲观锁更新***********************************************************************
     * 通过悲观锁来更新数据信息
     * @param id id
     */
    @Transactional(
            rollbackFor = Exception.class
    )
    public void updateForUpdate(int id){
        QueryWrapper<AcquireLockEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id)
                .last(" for update");

        AcquireLockEntity acquireLockEntity = getOne(queryWrapper);

        //可以在下面打上断点，然后在mysql的客户端 执行通用的 for update 或者 update语句 回发现线程处于等待中
        if(acquireLockEntity != null){
            acquireLockEntity.setRemark("这是一个测试");
            acquireLockEntity.setCreateTime(new Date());
            updateById(acquireLockEntity);
        }
    }



    /**
     * *********************************************乐观锁更新***********************************************************************
     * 通过悲观锁来更新数据信息
     * @param acquireLockEntity 需要更新的对象信息
     */
    @Transactional(
            rollbackFor = Exception.class
    )
    public void updateForVersion(AcquireLockEntity acquireLockEntity){
        QueryWrapper<AcquireLockEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",acquireLockEntity.getId())
                    .eq("version",acquireLockEntity.getVersion());
        acquireLockEntity.addVersion();
        updateById(acquireLockEntity);
    }

}
