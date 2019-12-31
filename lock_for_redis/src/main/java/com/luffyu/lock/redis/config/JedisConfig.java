package com.luffyu.lock.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>${description}</p>
 *
 * @author luffyu
 * @date 2019-12-30 16:27
 **/

@Configuration
public class JedisConfig {

    @Resource
    private RedisProperties jedisProperties;

    @Bean
    public JedisCluster getJedisCluster() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 截取集群节点
        String[] cluster = jedisProperties.getNodes().toArray(new String[0]);
        // 创建set集合
        Set<HostAndPort> nodes = new HashSet<>();
        // 循环数组把集群节点添加到set集合中
        for (String node : cluster) {
            String[] host = node.split(":");
            //添加集群节点
            nodes.add(new HostAndPort(host[0], Integer.parseInt(host[1])));
        }
        return new JedisCluster(nodes, jedisProperties.getConnectionTimeout(), jedisProperties.getSoTimeout(), jedisProperties.getMaxAttempts(), jedisProperties.getPassword(), poolConfig);
    }


}
