package com.backend.RestaurantPoll.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
@AllArgsConstructor
public class RestaurantDto {
    private Integer id;
    private String name;
    private List<UserDto> votingUsers;
    private List<MenuDto> menu;
}
