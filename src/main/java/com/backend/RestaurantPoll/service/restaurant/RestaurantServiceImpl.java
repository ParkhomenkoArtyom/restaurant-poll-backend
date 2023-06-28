package com.backend.RestaurantPoll.service.restaurant;

import com.backend.RestaurantPoll.controller.dto.request.MenuRequestDto;
import com.backend.RestaurantPoll.controller.dto.request.RestaurantRequestDto;
import com.backend.RestaurantPoll.controller.dto.response.MenuResponseDto;
import com.backend.RestaurantPoll.controller.dto.response.RestaurantResponseDto;
import com.backend.RestaurantPoll.controller.dto.response.UserResponseDto;
import com.backend.RestaurantPoll.entity.restaurant.Menu;
import com.backend.RestaurantPoll.entity.restaurant.Restaurant;
import com.backend.RestaurantPoll.entity.user.User;
import com.backend.RestaurantPoll.repository.restaurant.RestaurantRepository;
import com.backend.RestaurantPoll.service.restaurant.menu.MenuService;
import com.backend.RestaurantPoll.service.user.UserService;
import com.backend.RestaurantPoll.service.vote.VoteService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UserService userService;
    private final VoteService voteService;
    private final MenuService menuService;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, UserService userService, VoteService voteService, MenuService menuService) {
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
        this.voteService = voteService;
        this.menuService = menuService;
    }

    @Override
    public void addRestaurant(RestaurantRequestDto restaurant) {
        restaurantRepository.save(getFromDto(restaurant));
    }

    public void delete(Integer restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }

    @Override
    public void deleteRestaurantMenu(Integer restaurantId) {
        menuService.deleteRestaurantMenu(restaurantId);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.getAllRestaurants();
    }

    @Override
    public List<Integer> getAllRestaurantsIdx() {
        return restaurantRepository.getAllRestaurantsIds();
    }

    @Override
    public List<Restaurant> getRestaurantList() {
        return restaurantRepository.findAll();
    }

    private List<MenuResponseDto> restaurantMenuToDto(Set<Menu> menu) {
        return menu
                .stream()
                .map(dish -> new MenuResponseDto(dish.getDishName(), dish.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public Restaurant getFromDto(RestaurantRequestDto restaurantDto) {
        Restaurant restaurant = new Restaurant();
        if (restaurantDto.getId() != null) {
            restaurant.setId(restaurantDto.getId());
            deleteRestaurantMenu(restaurantDto.getId());
        }

        restaurant.setName(restaurantDto.getName());
        addMenuToRestaurant(restaurant, restaurantDto.getMenu());
        return restaurant;
    }

    @Override
    public void addMenuToRestaurant(Restaurant restaurant, List<MenuRequestDto> menuDto) {
        Set<Menu> menu = new LinkedHashSet<>(menuDto.size());
        for (MenuRequestDto dto : menuDto) {
            Menu dish = new Menu();
            dish.setRestaurant(restaurant);
            dish.setDishName(dto.getDishName());
            dish.setPrice(dto.getPrice());
            menu.add(dish);
        }
        restaurant.setMenu(menu);
    }

    private List<UserResponseDto> convertVotingUsersToDto(Restaurant restaurant) {
        List<User> users = voteService.getRestaurantVotingUsers(restaurant.getId());
        return users.stream().map(user -> new UserResponseDto(user.getId(), user.getName(),
                user.getRealName(), userService.getUserRoles(user))).collect(Collectors.toList());
    }

    @Override
    public RestaurantResponseDto convertToDto(Restaurant restaurant) {
        return new RestaurantResponseDto(restaurant.getId(), restaurant.getName().trim(),
                convertVotingUsersToDto(restaurant),
                restaurantMenuToDto(restaurant.getMenu()));
    }

    @Override
    public void addVoteToRestaurant(User user, Integer restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        voteService.saveNewRestaurantVote(user, restaurant);
    }
}
