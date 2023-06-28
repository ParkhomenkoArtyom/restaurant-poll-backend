package com.backend.RestaurantPoll.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MenuResponseDto {
    private String dishName;
    private Integer price;
}
