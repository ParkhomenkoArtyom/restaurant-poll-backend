package com.backend.RestaurantPoll.controller.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponseDto {
    private Integer id;
    private String username;
    private String realName;
    private List<String> roles;

    public UserResponseDto(Integer id, String username,String realName, List<String> roles){
        this.id = id;
        this.username = username;
        this.realName = realName;
        this.roles = roles;
    }
}