package com.luffyu.lock.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

    public AcquireLockEntity() {
    }


    public AcquireLockEntity(String methodName) {
        this.methodName = methodName;
        this.createTime = new Date();
    }
}
