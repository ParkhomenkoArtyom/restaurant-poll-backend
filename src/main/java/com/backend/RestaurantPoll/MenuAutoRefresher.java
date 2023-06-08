package com.backend.RestaurantPoll;

import com.backend.RestaurantPoll.entity.Dish;
import com.backend.RestaurantPoll.entity.Menu;
import com.backend.RestaurantPoll.entity.Restaurant;
import com.backend.RestaurantPoll.service.dish.DishService;
import com.backend.RestaurantPoll.service.menu.MenuService;
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
public class MenuAutoRefresher {
    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final DishService dishService;
    private final VoteService voteService;

    public MenuAutoRefresher(RestaurantService restaurantService, MenuService menuService, DishService dishService, VoteService voteService) {
        this.restaurantService = restaurantService;
        this.menuService = menuService;
        this.dishService = dishService;
        this.voteService = voteService;
    }

    private void deleteAllRestaurantMenu(List<Integer> ids) {
        for (Integer id : ids) {
            restaurantService.deleteRestaurantMenu(id);
        }
    }

    private void setRandomMenusToRestaurants() {
        Integer availableDishesCount = dishService.getDishesCount();
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        for (Restaurant restaurant : restaurants)
            setRandomRestaurantMenu(availableDishesCount, restaurant);
    }

    private void setRandomRestaurantMenu(Integer dishesCount, Restaurant restaurant) {
        int menuSize = 2;

        RandomUtil randomUtil = new RandomUtil(dishesCount);
        List<Integer> randomDishesIdx = new ArrayList<>();

        for (int i = 0; i < menuSize; i++) {
            randomDishesIdx.add(randomUtil.generate());
        }

        for (Integer i : randomDishesIdx) {
            Dish randomDish = dishService.getDishById(i + 1);
            Menu menuDish = new Menu();
            menuDish.setRestaurant(restaurant);
            menuDish.setDishName(randomDish.getDish());
            menuDish.setPrice(randomDish.getPrice());
            menuService.saveMenu(menuDish);
        }
    }

    // Refresh votes and restaurants menus every day at 12 am.
    @Scheduled(cron = "0 0 0 * * ?", zone = "Europe/Moscow")
    public void refreshRestaurantMenusAndVotes() {
        voteService.removeAll();
        deleteAllRestaurantMenu(restaurantService.getAllRestaurantsIdx());
        setRandomMenusToRestaurants();
    }
}