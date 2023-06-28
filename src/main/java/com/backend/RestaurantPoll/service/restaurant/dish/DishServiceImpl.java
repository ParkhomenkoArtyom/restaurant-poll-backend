package com.backend.RestaurantPoll.service.restaurant.dish;

import com.backend.RestaurantPoll.entity.restaurant.Dish;
import com.backend.RestaurantPoll.repository.restaurant.DishRepository;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public Dish getDishById(Integer id) {
        return dishRepository.findById(id).orElseThrow();
    }

    @Override
    public Integer getDishesCount() {
        return dishRepository.dishesCount();
    }
}
