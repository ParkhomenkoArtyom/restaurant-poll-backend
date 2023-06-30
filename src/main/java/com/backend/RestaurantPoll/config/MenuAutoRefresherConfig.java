package com.backend.RestaurantPoll.config;

import com.backend.RestaurantPoll.entity.restaurant.Dish;
import com.backend.RestaurantPoll.entity.restaurant.Menu;
import com.backend.RestaurantPoll.entity.restaurant.Restaurant;
import com.backend.RestaurantPoll.service.restaurant.dish.DishService;
import com.backend.RestaurantPoll.service.restaurant.menu.MenuService;
import com.backend.RestaurantPoll.service.restaurant.RestaurantService;
import com.backend.RestaurantPoll.service.vote.VoteService;
import com.backend.RestaurantPoll.util.RandomUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
 * This class deletes user
 * votes at the end of the day and
 * adds a new restaurant menu if the administrator
 *  did not do it himself during the day
 */

@Component
public class MenuAutoRefresherConfig {
    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final DishService dishService;
    private final VoteService voteService;

    public MenuAutoRefresherConfig(RestaurantService restaurantService, MenuService menuService,
                                   DishService dishService, VoteService voteService) {
        this.restaurantService = restaurantService;
        this.menuService = menuService;
        this.dishService = dishService;
        this.voteService = voteService;
    }

    private void setRandomMenusToRestaurants() {
        Integer availableDishesCount = dishService.getDishesCount();
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        for (Restaurant restaurant : restaurants)
            setRandomRestaurantMenu(availableDishesCount, restaurant);
    }
    private void setRandomRestaurantMenu(Integer availableDishesCount, Restaurant restaurant) {
        int menuSize = 3;
        int currentMenuSize = availableDishesCount <= menuSize
                ? availableDishesCount
                : menuSize;

        RandomUtil randomUtil = new RandomUtil(availableDishesCount);
        List<Integer> randomDishesIds = new ArrayList<>();

        for (int i = 0; i < currentMenuSize; i++) {
            randomDishesIds.add(randomUtil.generate());
        }

        for (int randomDishId : randomDishesIds) {
            Dish randomDish = dishService.getDishById(randomDishId + 1);
            Menu menuDish = new Menu(randomDish.getDish(),randomDish.getPrice(), restaurant);
            menuService.saveMenu(menuDish);
        }
    }

    // Refresh votes and restaurants menus every day at 12 am.
    @Scheduled(cron = "0 0 0 * * ?", zone = "Europe/Moscow")
    public void refreshRestaurantMenusAndVotes() {
        voteService.removeAll();

        for (Integer restaurantId : restaurantService.getAllRestaurantsIdx()) {
            restaurantService.deleteRestaurantMenu(restaurantId);
        }
        setRandomMenusToRestaurants();
    }
}