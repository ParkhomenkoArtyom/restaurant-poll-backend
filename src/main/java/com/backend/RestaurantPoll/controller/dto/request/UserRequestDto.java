package com.backend.RestaurantPoll.controller.dto.request;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserRequestDto {
    private String username;
    private String realName;
    private String password;

    public UserRequestDto(String username,String realName, String password){
        this.username = username;
        this.realName = realName;
        this.password = password;
    }
}