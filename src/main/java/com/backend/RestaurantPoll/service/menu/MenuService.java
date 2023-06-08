package com.backend.RestaurantPoll.service.menu;

import com.backend.RestaurantPoll.dto.MenuDto;
import com.backend.RestaurantPoll.entity.Menu;

public interface MenuService {
    void deleteRestaurantMenu(Integer restaurantId);

    void saveMenu(Menu menu);
}
