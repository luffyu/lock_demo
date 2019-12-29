package com.luffyu.lock.zookeeper.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>${description}</p>
 *
 * @author luffyu
 * @date 2019-12-29 11:44
 **/
@Data
@Component
@ConfigurationProperties(prefix = "curator")
public class CuratorProperties {

    /**
     * 重试次数
     */
    private int retryCount;

    /**
     * 重试间隔时间
     */
    private int elapsedTimeMs;

    /**
     * zookeeper 地址
     */
    private String connectString;

    /**
     * session超时时间
     */
    private int sessionTimeoutMs;

    /**
     * 连接超时时间
     */
    private int connectionTimeoutMs;


}
