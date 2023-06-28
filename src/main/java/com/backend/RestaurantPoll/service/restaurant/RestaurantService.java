package com.backend.RestaurantPoll.service.restaurant;

import com.backend.RestaurantPoll.controller.dto.request.MenuRequestDto;
import com.backend.RestaurantPoll.controller.dto.request.RestaurantRequestDto;
import com.backend.RestaurantPoll.controller.dto.response.RestaurantResponseDto;
import com.backend.RestaurantPoll.entity.restaurant.Menu;
import com.backend.RestaurantPoll.entity.restaurant.Restaurant;
import com.backend.RestaurantPoll.entity.user.User;

import java.util.List;
import java.util.Set;

public interface RestaurantService {
    void addRestaurant(RestaurantRequestDto restaurant);

    List<Restaurant> getRestaurantList();

    RestaurantResponseDto convertToDto(Restaurant restaurant);

    Restaurant getFromDto(RestaurantRequestDto restaurantDto);

    void addVoteToRestaurant(User user, Integer restaurantId);

    void deleteRestaurantMenu(Integer restaurantId);

    List<Restaurant> getAllRestaurants();

    List<Integer> getAllRestaurantsIdx();

    Set<Menu> addMenuToRestaurant(Restaurant restaurant, List<MenuRequestDto> menuDto);
}
