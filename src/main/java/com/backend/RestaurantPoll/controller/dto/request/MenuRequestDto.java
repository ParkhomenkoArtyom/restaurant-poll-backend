package com.backend.RestaurantPoll.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MenuRequestDto {
    private String dishName;
    private Integer price;
}
