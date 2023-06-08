package com.backend.RestaurantPoll.repository;

import com.backend.RestaurantPoll.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Integer> {
    @Query("SELECT count(*) FROM Dish")
    Integer dishesCount();
    Optional<Dish> findById(Integer id);
}
