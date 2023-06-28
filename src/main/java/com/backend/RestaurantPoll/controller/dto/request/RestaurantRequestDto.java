package com.backend.RestaurantPoll.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class RestaurantRequestDto {
    private Integer id;
    private String name;
    private List<MenuRequestDto> menu;
}
