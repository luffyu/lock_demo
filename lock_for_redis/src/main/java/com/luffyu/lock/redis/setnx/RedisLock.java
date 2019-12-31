package com.luffyu.lock.redis.setnx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.params.SetParams;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>${description}</p>
 *
 * @author luffyu
 * @date 2019-12-30 16:33
 **/
@Service
public class RedisLock {

    @Autowired
    private JedisCluster jedisCluster;


    /**
     * 不安全的设置锁的过期时间
     *
     * 不是原子操作 可能会存在时间没有设置成功
     *
     * @param key key值
     * @return 返回true表示获取到了锁 并设置过期时间成功
     */
    public boolean tryAcquireLockUnSafe(String key){
        if(jedisCluster.setnx(key,"true") > 1){
            jedisCluster.expire(key,10);
            return true;
        }
        return false;
    }

    /**
     * 不安全的删除锁
     * @param key 当前的key
     */
    public boolean tryReleaseLockUnSafe(String key){
        if(jedisCluster.del(key) > 0){
            return true;
        }
        return false;
    }

    /**
     * 设置分布式锁  和过期时间
     * @param key 当前的key值
     * @param value 当前的value值
     * @return 返回ture表示获取到了锁 并且设置过期时间成功
     */
    public boolean tryAcquireLockSafe(String key,String value){
        SetParams setParams = new SetParams();
        setParams.nx().ex(3);
        String aTrue = jedisCluster.set(key, value, setParams);
        if("OK".equalsIgnoreCase(aTrue)){
            return true;
        }
        return false;
    }


    /**
     * 删除key值
     */
    private static final String DEL_SCRIPT = "if redis.call('GET',KEYS[1]) == ARGV[1]\n" +
            "then \n" +
            "\treturn redis.call(\"del\",KEYS[1])\n" +
            "else\n" +
            "    return 0\n" +
            "end\n";

    /**
     * 释放锁资源
     * @param key 当前key
     * @param value 之前设置进去的值
     * @return 返回true表示设置成功 false表示设置失败
     */
    public boolean tryReleaseLockSafe(String key,String value){
        try{
            List<String> keys = new ArrayList<>(1);
            keys.add(key);
            List<String> args = new ArrayList<>(2);
            args.add(value);
            Object eval = jedisCluster.eval(DEL_SCRIPT, keys, args);
            int delResult = Integer.valueOf(eval.toString());
            if(delResult > 0){
                return true;
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
