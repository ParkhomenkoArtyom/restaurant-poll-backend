package com.backend.RestaurantPoll.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;
@Getter
@Setter
@ToString
@AllArgsConstructor
public class RestaurantResponseDto {
    private Integer id;
    private String name;
    private List<UserResponseDto> votingUsers;
    private List<MenuResponseDto> menu;
}
