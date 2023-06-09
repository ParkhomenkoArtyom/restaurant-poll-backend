package com.backend.RestaurantPoll.controller;

import com.backend.RestaurantPoll.dto.RestaurantDto;
import com.backend.RestaurantPoll.entity.Restaurant;
import com.backend.RestaurantPoll.entity.User;
import com.backend.RestaurantPoll.service.restaurant.RestaurantService;
import com.backend.RestaurantPoll.service.user.UserService;
import com.backend.RestaurantPoll.service.vote.VoteService;
import com.backend.RestaurantPoll.util.AuthUserUtil;
import com.backend.RestaurantPoll.util.DailyVotingPeriodUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
@RequestMapping("api/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final VoteService voteService;

    public RestaurantController(RestaurantService restaurantService, UserService userService, VoteService voteService) {
        this.restaurantService = restaurantService;
        this.userService = userService;
        this.voteService = voteService;
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/add-update")
    public ResponseEntity<?> addRestaurant(@RequestBody RestaurantDto restaurantDto) {
        restaurantService.addRestaurant(restaurantDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/list")
    public List<RestaurantDto> getListOfRestaurants() {
        List<Restaurant> restaurantList = restaurantService.getRestaurantList();
        return restaurantList.stream().map(restaurantService::convertToDto).toList();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/vote/{id}")
    public List<RestaurantDto> voteOnRestaurant(@PathVariable Integer id) {
        if (DailyVotingPeriodUtil.isVotingValid()) {
            User user = userService.findByName(AuthUserUtil.getAuthenticationUsername());
            if (userService.isVotingUserExist(user.getId()))
                voteService.removeVote(user.getId());

            restaurantService.addVoteToRestaurant(user, id);
        }
        return getListOfRestaurants();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/remove-vote")
    public List<RestaurantDto> removeUserVote() {
        User user = userService.findByName(AuthUserUtil.getAuthenticationUsername());
        if (userService.isVotingUserExist(user.getId()))
            voteService.removeVote(user.getId());
        return getListOfRestaurants();
    }
}
