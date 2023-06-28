package com.backend.RestaurantPoll.service.restaurant.menu;

import com.backend.RestaurantPoll.entity.restaurant.Menu;
import com.backend.RestaurantPoll.repository.restaurant.MenuRepository;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void deleteRestaurantMenu(Integer restaurantId) {
        menuRepository.removeByRestaurantId(restaurantId);
    }

    @Override
    public void saveMenu(Menu menu) {
        menuRepository.save(menu);
    }
}
