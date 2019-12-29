package com.luffyu.lock.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.Date;

/**
 * <p>${description}</p>
 *
 * @author luffyu
 * @date 2019-12-28 16:21
 **/
@Data
@TableName("acquire_lock")
public class AcquireLockEntity {

    /**
     * 自增id
     */
    @TableId
    private Integer id;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 描述
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 版本号
     * mybatis-plus 可以通过 @Version 注解来实现版本号
     */
    private Integer version;


    public AcquireLockEntity() {
    }


    public AcquireLockEntity(String methodName) {
        this.methodName = methodName;
        this.createTime = new Date();
    }



    public int addVersion(){
        if(this.version == null){
            this.version = 0;
        }
        this.version ++;

        return this.version;
    }
}
