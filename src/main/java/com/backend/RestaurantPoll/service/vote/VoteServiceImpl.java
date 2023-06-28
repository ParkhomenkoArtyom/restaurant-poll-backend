package com.backend.RestaurantPoll.service.vote;

import com.backend.RestaurantPoll.entity.restaurant.Restaurant;
import com.backend.RestaurantPoll.entity.user.User;
import com.backend.RestaurantPoll.entity.vote.Vote;
import com.backend.RestaurantPoll.repository.vote.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;

    public VoteServiceImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public void saveNewRestaurantVote(User user, Restaurant restaurant) {
        voteRepository.save(new Vote(restaurant, user));
    }

    @Override
    public List<User> getRestaurantVotingUsers(Integer restaurantId) {
        return voteRepository.getRestaurantVotingUsersIds(restaurantId).orElseThrow();
    }
    @Override
    public void removeVote(Integer useId) {
        voteRepository.remove(useId);
    }

    @Override
    public void removeAll() {
        voteRepository.removeAll();
    }
}
