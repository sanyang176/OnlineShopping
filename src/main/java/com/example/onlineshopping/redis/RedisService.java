package com.example.onlineshopping.redis;

public interface RedisService {
    // 设置键值
    void SetKeyValue(String key,Object value);

    // 获取键值
    Object TryGetKeyValue(String key) ;

    Boolean GetRedisLock(String lockKey,String uuid,long expireTime);

    void RemoveRedisLock(String lockKey,String uuid);

    void DeleteKeyValue(String key) throws InterruptedException;

    void ClearRedisLock();
}
