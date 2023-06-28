package com.backend.RestaurantPoll.service.vote;

import com.backend.RestaurantPoll.entity.restaurant.Restaurant;
import com.backend.RestaurantPoll.entity.user.User;

import java.util.List;

public interface VoteService {
    void saveNewRestaurantVote(User user, Restaurant restaurant);

    List<User> getRestaurantVotingUsers(Integer restaurantId);

    void removeVote(Integer useId);

    void removeAll();
}
