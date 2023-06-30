package com.backend.RestaurantPoll.controller;

import com.backend.RestaurantPoll.controller.dto.request.RestaurantRequestDto;
import com.backend.RestaurantPoll.controller.dto.response.RestaurantResponseDto;
import com.backend.RestaurantPoll.entity.restaurant.Restaurant;
import com.backend.RestaurantPoll.entity.user.User;
import com.backend.RestaurantPoll.exception.RestaurantNotFoundException;
import com.backend.RestaurantPoll.exception.UserNotFoundException;
import com.backend.RestaurantPoll.service.restaurant.RestaurantService;
import com.backend.RestaurantPoll.service.user.UserService;
import com.backend.RestaurantPoll.service.vote.VoteService;
import com.backend.RestaurantPoll.util.AuthUserUtil;
import com.backend.RestaurantPoll.util.DailyVotingPeriodUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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

    @PostMapping("/add-update")
    public ResponseEntity<?> addRestaurant(@RequestBody RestaurantRequestDto restaurantDto) {
        try {
            restaurantService.addRestaurant(restaurantDto);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/list")
    public List<RestaurantResponseDto> getListOfRestaurants() {
        List<Restaurant> restaurantList = restaurantService.getRestaurantList();
        return restaurantList.stream().map(restaurantService::convertToDto).toList();
    }

    @GetMapping("/vote/{id}")
    public ResponseEntity<?> voteOnRestaurant(@PathVariable Integer id)  {
        if (DailyVotingPeriodUtil.isVotingValid()) {
            User user = userService.findByName(AuthUserUtil.getAuthenticationUsername());
            if (userService.isVotingUserExist(user.getId()))
                voteService.removeVote(user.getId());

            try {
                restaurantService.addVoteToRestaurant(user, id);
            } catch (RestaurantNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            }
        }
        return ResponseEntity.ok(getListOfRestaurants());
    }

    @GetMapping("/remove-vote")
    public List<RestaurantResponseDto> removeUserVote() {
        User user = userService.findByName(AuthUserUtil.getAuthenticationUsername());
        if (userService.isVotingUserExist(user.getId()))
            voteService.removeVote(user.getId());
        return getListOfRestaurants();
    }
}
