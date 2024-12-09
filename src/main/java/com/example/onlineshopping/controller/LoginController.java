package com.example.onlineshopping.controller;

import com.example.onlineshopping.constants.Constants;
import com.example.onlineshopping.redis.RedisService;
import com.example.onlineshopping.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/login/mainPage")
    public String login(Model model, HttpServletRequest request) {
        var username = request.getParameter("username");
        var password = request.getParameter("password");
        var isLoginSuccess = loginService.IsLoginInSuccess(username, password);
        redisService.SetKeyValue(Constants.RedisLoginKeyConstant,username);
        model.addAttribute("loginStatus",loginService.SetLoginStatus(isLoginSuccess));
        model.addAttribute("userName",isLoginSuccess? "用户" + username: "");
        return isLoginSuccess ? "index" : "login";
    }

    @RequestMapping("/login")
    public String Init(){
        return "login";
    }

    @RequestMapping("/register")
    public String Register(HttpServletRequest request){
        var username = request.getParameter("username");
        var password = request.getParameter("password");
        if(username == null || password == null){ return "register";}
        var isValidUserName = loginService.ValidateUserName(username);
        if(isValidUserName){
            loginService.SaveUserInfo(username,password);
        }
        return isValidUserName? "login":"register";
    }
}