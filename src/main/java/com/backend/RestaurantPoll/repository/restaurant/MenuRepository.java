package com.backend.RestaurantPoll.repository.restaurant;

import com.backend.RestaurantPoll.entity.restaurant.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Transactional @Modifying
    void removeByRestaurantId(Integer restaurantId);
}
