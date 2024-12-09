package com.example.onlineshopping.service;

import com.example.onlineshopping.entity.UserInfo;

import java.util.List;

public interface LoginService {
    boolean IsLoginInSuccess(String username, String password);
    String SetLoginStatus(boolean isLoginSuccess);
    boolean ValidateUserName(String username);
    void SaveUserInfo(String username,String password);
    List<UserInfo> GetUserInfos();
}
