package com.backend.RestaurantPoll.service.restaurant.menu;

import com.backend.RestaurantPoll.entity.restaurant.Menu;

public interface MenuService {
    void deleteRestaurantMenu(Integer restaurantId);
    void saveMenu(Menu menu);
}
