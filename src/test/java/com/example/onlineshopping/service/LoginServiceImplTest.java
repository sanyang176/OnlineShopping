package com.example.onlineshopping.service;

import com.example.onlineshopping.entity.UserInfo;
import com.example.onlineshopping.mapping.UserMapper;
import com.example.onlineshopping.redis.RedisService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

    @InjectMocks
    LoginServiceImpl loginService;

    @Mock(lenient = true)
    UserMapper userMapper;

    @Mock(lenient = true)
    RedisService redisService;

    @Test
    void TestGetUserInfosForNonRedisKey() {
        //Arrange
        var userInfos = new ArrayList<UserInfo>();
        var userInfo = new UserInfo();
        userInfo.setUsername("2");
        userInfo.setPassword("3");
        userInfo.setId(1);
        userInfos.add(userInfo);
        Mockito.when(redisService.TryGetKeyValue(Mockito.anyString())).thenReturn(null);
        Mockito.when(userMapper.findAll()).thenReturn(userInfos);

        //Act
        var result = loginService.GetUserInfos();

        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(userInfos, result);
    }

    @Test
    void TestGetUserInfosForRedisKey() {
        //Arrange
        var userInfos = new ArrayList<UserInfo>();
        var userInfo = new UserInfo();
        userInfo.setUsername("2");
        userInfo.setPassword("3");
        userInfo.setId(1);
        userInfos.add(userInfo);
        Mockito.when(redisService.TryGetKeyValue(Mockito.anyString())).thenReturn(userInfos);
        Mockito.when(userMapper.findAll()).thenReturn(userInfos);

        //Act
        var result = loginService.GetUserInfos();

        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(userInfos, result);
    }

    @Test
    void TestIsLoginInSuccess() {
        //Arrange
        var userInfos = new ArrayList<UserInfo>();
        var userInfo = new UserInfo();
        userInfo.setUsername("2");
        userInfo.setPassword("3");
        userInfo.setId(1);
        userInfos.add(userInfo);
        Mockito.when(redisService.TryGetKeyValue(Mockito.anyString())).thenReturn(userInfos);
        Mockito.when(userMapper.findAll()).thenReturn(userInfos);

        //Act
        var result = loginService.IsLoginInSuccess("2","3");

        //Assert
        assertTrue(result);
    }

    @Test
    void TestSetLoginStatus() {
        //Act
        var result = loginService.SetLoginStatus(true);

        //Assert
        assertEquals("退出", result);
    }

    @Test
    void TestValidateUserName() {
        //Arrange
        var userInfos = new ArrayList<UserInfo>();
        var userInfo = new UserInfo();
        userInfo.setUsername("2");
        userInfo.setPassword("3");
        userInfo.setId(1);
        userInfos.add(userInfo);
        Mockito.when(redisService.TryGetKeyValue(Mockito.anyString())).thenReturn(userInfos);
        Mockito.when(userMapper.findAll()).thenReturn(userInfos);

        //Act
        var result = loginService.ValidateUserName("3");

        //Assert
        assertTrue(result);
    }

    @Test
    void TestSaveUserInfo() {
        //Arrange
        var userInfos = new ArrayList<UserInfo>();
        var userInfo = new UserInfo();
        userInfo.setUsername("2");
        userInfo.setPassword("3");
        userInfo.setId(1);
        userInfos.add(userInfo);
        Mockito.when(redisService.TryGetKeyValue(Mockito.anyString())).thenReturn(userInfos);
        Mockito.when(userMapper.findAll()).thenReturn(userInfos);

        //Act
        loginService.SaveUserInfo("2","3");

        //Assert
    }
}