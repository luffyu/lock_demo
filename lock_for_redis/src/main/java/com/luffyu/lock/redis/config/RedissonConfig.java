package com.luffyu.lock.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * <p>${description}</p>
 *
 * @author luffyu
 * @date 2019-12-30 18:08
 **/
@Configuration
public class RedissonConfig {


    @Autowired
    private RedisProperties redisProperties;



    @Bean
    public RedissonClient getRedisson() {
        String[] nodes = new String[redisProperties.getNodes().size()];
        //redisson版本是3.5，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
        for (int i = 0; i < redisProperties.getNodes().size(); i++) {
            nodes[i] = "redis://" +  redisProperties.getNodes().get(i);
        }

        Config config = new Config();
        //这是用的集群server
        config.useClusterServers()
                //设置集群状态扫描时间
                .setScanInterval(2000)
                .addNodeAddress(nodes)
                .setPassword(redisProperties.getPassword());

        return Redisson.create(config);
    }

}
