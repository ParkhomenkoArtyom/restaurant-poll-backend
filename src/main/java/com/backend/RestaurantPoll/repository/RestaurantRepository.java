package com.backend.RestaurantPoll.repository;

import com.backend.RestaurantPoll.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    Optional<Restaurant> findById(Integer name);

    @Query("SELECT rest FROM Restaurant rest")
    List<Restaurant> findAll();

    @Query("SELECT r  FROM Restaurant r")
    List<Restaurant> getAllRestaurants();

    @Query("SELECT r.id  FROM Restaurant r")
    List<Integer> getAllRestaurantsIds();

}
