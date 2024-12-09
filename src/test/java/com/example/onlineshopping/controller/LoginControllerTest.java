package com.example.onlineshopping.controller;

import com.example.onlineshopping.redis.RedisService;
import com.example.onlineshopping.service.LoginService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @InjectMocks
    LoginController loginController;

    @Mock(lenient = true)
    LoginService loginService;

    @Mock(lenient = true)
    RedisService redisService;

    @Mock(lenient = true)
    HttpServletRequest request;

    @ParameterizedTest
    @MethodSource("LoginProvider")
    void TestLogin(boolean isLoginSuccess,String assertResult) {
        //Arrange
        Mockito.when(loginService.IsLoginInSuccess(null,null)).thenReturn(isLoginSuccess);

        //Act
        var result = loginController.login(new ConcurrentModel(),new HttpServletRequestWrapper(Mockito.mock(HttpServletRequest.class)));

        //Assert
        assertEquals(assertResult,result);
    }

    @Test
    void TestInit() {
        //Act
        var result = loginController.Init();

        //Assert
        assertEquals("login",result);
    }

    @ParameterizedTest
    @MethodSource("RegisterArgumentProvider")
    void TestRegister(String username,boolean isValidateSuccess,String assertResult) {
        //Arrange
        Mockito.when(loginService.ValidateUserName(Mockito.anyString())).thenReturn(isValidateSuccess);
        Mockito.when(request.getParameter(Mockito.anyString())).thenReturn(username);

        //Act
        var result = loginController.Register(request);

        //Assert
        assertEquals(assertResult,result);
    }

    static Stream<Arguments> LoginProvider() {
        return Stream.of(
                Arguments.of(true,"index"),
                Arguments.of(false,"login")
        );
    }

    static Stream<Arguments> RegisterArgumentProvider() {
        return Stream.of(
                Arguments.of(null,false,"register"),
                Arguments.of("123",false,"register"),
                Arguments.of("123",true,"login")
        );
    }
}