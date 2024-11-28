package com.example.onlineshopping.redis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedisServiceImplTest {

    @InjectMocks
    RedisServiceImpl redisServiceImpl;

    @Mock(lenient = true)
    RedisTemplate redisTemplate;

    @BeforeEach
    public void Init() {
        var valueOperations = Mockito.mock(ValueOperations.class);
        var setOperations = Mockito.mock(SetOperations.class);
        var hashOperations = Mockito.mock(HashOperations.class);
        var listOperations = Mockito.mock(ListOperations.class);
        var zSetOperations = Mockito.mock(ZSetOperations.class);
        when(redisTemplate.opsForSet()).thenReturn(setOperations);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        when(redisTemplate.opsForList()).thenReturn(listOperations);
        when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
    }

    @Test
    void setKeyValue() {

        //Act
        redisServiceImpl.SetKeyValue("1","2");

        //Assert
    }

    @Test
    void deleteKeyValue() {
        redisServiceImpl.DeleteKeyValue("1");
    }

    @Test
    void tryGetKeyValue() {
        redisServiceImpl.TryGetKeyValue("1");
    }

    @Test
    void getRedisLock() {
        redisServiceImpl.GetRedisLock("1","1",2);
    }

    @Test
    void removeRedisLock() {
        redisServiceImpl.RemoveRedisLock("1","1");
    }

    @Test
    void clearRedisLock() {
        redisServiceImpl.ClearRedisLock();
    }}