package com.example.onlineshopping.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInfo {
    private Integer id;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}