package com.backend.RestaurantPoll.service.restaurant.dish;

import com.backend.RestaurantPoll.entity.restaurant.Dish;

public interface DishService {
    Dish getDishById(Integer id);

    Integer getDishesCount();
}
