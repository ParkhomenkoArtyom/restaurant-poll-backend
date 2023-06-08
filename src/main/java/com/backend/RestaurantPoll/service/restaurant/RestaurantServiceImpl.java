package com.backend.RestaurantPoll.service.restaurant;

import com.backend.RestaurantPoll.dto.MenuDto;
import com.backend.RestaurantPoll.dto.RestaurantDto;
import com.backend.RestaurantPoll.dto.UserDto;
import com.backend.RestaurantPoll.entity.Menu;
import com.backend.RestaurantPoll.entity.Restaurant;
import com.backend.RestaurantPoll.entity.User;
import com.backend.RestaurantPoll.repository.RestaurantRepository;
import com.backend.RestaurantPoll.service.menu.MenuService;
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
    public void addRestaurant(RestaurantDto restaurant) {
        Restaurant restaurant1 = getFromDto(restaurant);
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

    private List<MenuDto> restaurantMenuToDto(Set<Menu> menu) {
        return menu
                .stream()
                .map(dish -> new MenuDto(dish.getDishName(), dish.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public Restaurant getFromDto(RestaurantDto restaurantDto) {
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
    public void addMenuToRestaurant(Restaurant restaurant, List<MenuDto> menuDto) {
        Set<Menu> menu = new LinkedHashSet<>(menuDto.size());
        for (MenuDto dto : menuDto) {
            Menu dish = new Menu();
            dish.setRestaurant(restaurant);
            dish.setDishName(dto.getDishName());
            dish.setPrice(dto.getPrice());
            menu.add(dish);
        }
        restaurant.setMenu(menu);
    }

    private List<UserDto> convertVotingUsersToDto(Restaurant restaurant) {
        List<User> users = voteService.getRestaurantVotingUsers(restaurant.getId());
        return users.stream().map(user -> new UserDto(user.getId(), user.getName(),
                user.getRealName(), userService.getUserRoles(user))).collect(Collectors.toList());
    }

    @Override
    public RestaurantDto convertToDto(Restaurant restaurant) {
        return new RestaurantDto(restaurant.getId(), restaurant.getName().trim(),
                convertVotingUsersToDto(restaurant),
                restaurantMenuToDto(restaurant.getMenu()));
    }

    @Override
    public void addVoteToRestaurant(User user, Integer restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        voteService.saveNewRestaurantVote(user, restaurant);
    }
}
