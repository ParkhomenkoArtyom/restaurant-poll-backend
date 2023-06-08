package com.backend.RestaurantPoll.service.restaurant;

import com.backend.RestaurantPoll.dto.MenuDto;
import com.backend.RestaurantPoll.dto.RestaurantDto;
import com.backend.RestaurantPoll.entity.Restaurant;
import com.backend.RestaurantPoll.entity.User;

import java.util.List;

public interface RestaurantService {
    void addRestaurant(RestaurantDto restaurant);

    List<Restaurant> getRestaurantList();

    RestaurantDto convertToDto(Restaurant restaurant);

    Restaurant getFromDto(RestaurantDto restaurantDto);

    void addVoteToRestaurant(User user, Integer restaurantId);

    void deleteRestaurantMenu(Integer restaurantId);

    List<Restaurant> getAllRestaurants();

    List<Integer> getAllRestaurantsIdx();

    void addMenuToRestaurant(Restaurant restaurant, List<MenuDto> menuDto);
}
