package com.example.onlineshopping.service;

import com.example.onlineshopping.constants.Constants;
import com.example.onlineshopping.entity.UserInfo;
import com.example.onlineshopping.mapping.UserMapper;
import com.example.onlineshopping.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisService redisService;

    public List<UserInfo> GetUserInfos(){
        var object = redisService.TryGetKeyValue(Constants.RedisUserInfoStockKeyConstant);
        List<UserInfo> userInfos;
        if(object != null){
            var userInfoList = object instanceof List<?>? (List<UserInfo>)object : null;
            userInfos = userInfoList== null? userMapper.findAll() : userInfoList;
        }
        else{
            userInfos = userMapper.findAll();
            redisService.SetKeyValue(Constants.RedisUserInfoStockKeyConstant, userInfos);
        }

        return userInfos;
    }

    public boolean IsLoginInSuccess(String username, String password) {
        var userInfos = GetUserInfos();
        var userInfo = userInfos.stream().filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password)).findFirst();
        return userInfo.isPresent();
    }

    public String SetLoginStatus(boolean isLoginSuccess){
        return isLoginSuccess? "退出":"登录";
    }

    public boolean ValidateUserName(String username) {
        var userInfoList = GetUserInfos();
        var userInfo = userInfoList.stream().filter(user -> user.getUsername().equals(username)).findFirst();
        return !userInfo.isPresent();
    }

    public void SaveUserInfo(String username,String password) {
        var userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userMapper.save(userInfo);
        var object = redisService.TryGetKeyValue(Constants.RedisUserInfoStockKeyConstant);
        var userInfoList = object instanceof List<?>? (List<UserInfo>)object : null;
        if(userInfoList != null){
            userInfoList.add(userInfo);
        }
        redisService.SetKeyValue(Constants.RedisUserInfoStockKeyConstant, userInfoList);
    }}
