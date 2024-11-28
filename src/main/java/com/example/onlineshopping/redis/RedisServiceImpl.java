package com.example.onlineshopping.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisServiceImpl implements RedisService{
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 设置键值
    public void SetKeyValue(String key, Object value) {
        try {
            //SET命令返回OK ，则证明获取锁成功
            var d = redisTemplate.opsForValue();
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            log.error("Failed to set lock.",e);
        }
    }

    public void DeleteKeyValue(String key) {
        try {
            //SET命令返回OK ，则证明获取锁成功
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("Failed to set lock.",e);
        }
    }

    // 获取键值
    public Object TryGetKeyValue(String key) {
        if(Boolean.TRUE.equals(redisTemplate.hasKey(key))){
            return redisTemplate.opsForValue().get(key);
        }
        return null;
    }

    public Boolean GetRedisLock(String lockKey,String uuid,long expireTime){
        try {
            //SET命令返回OK ，则证明获取锁成功
            var setResult = redisTemplate.opsForValue().setIfAbsent(lockKey, uuid, expireTime, TimeUnit.SECONDS);
            var a = redisTemplate.hasKey(lockKey);
            return setResult;
        } catch (Exception e) {
            log.error("Failed to get lock.",e);
            return false;
        }
    }

    public void RemoveRedisLock(String lockKey,String uuid){
        try
        {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
            //redis脚本执行
            redisTemplate.execute(redisScript, Collections.singletonList(lockKey),uuid);
        } catch (Exception e) {
            log.error("Failed to delete lock.",e);
        }
    }

    public void ClearRedisLock(){
        Set<String> keys = redisTemplate.keys("*");
        if (keys != null) {
            redisTemplate.delete(keys);
        }
    }
}
