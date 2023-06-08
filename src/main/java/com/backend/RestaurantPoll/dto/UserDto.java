package com.backend.RestaurantPoll.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private Integer id;
    private String username;
    private String realName;
    private String password;
    private List<String> roles;

    public UserDto(Integer id, String username,String realName, List<String> roles){
        this.id = id;
        this.username = username;
        this.realName = realName;
        this.roles = roles;
    }
}
