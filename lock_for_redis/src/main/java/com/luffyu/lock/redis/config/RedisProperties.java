package com.luffyu.lock.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: WangXue
 * @create: 2018-09-03 18:12
 */
@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
@Data
public class RedisProperties {

    /**
     * master nodes
     */
    private List<String> nodes;

    private Integer maxAttempts;
    private Integer connectionTimeout;
    private Integer soTimeout;
    private String password;
    private Integer maxActive;
    private Integer maxIdle;

}
