package com.backend.RestaurantPoll.service.dish;

import com.backend.RestaurantPoll.entity.Dish;

public interface DishService {
    Dish getDishById(Integer id);

    Integer getDishesCount();
}
