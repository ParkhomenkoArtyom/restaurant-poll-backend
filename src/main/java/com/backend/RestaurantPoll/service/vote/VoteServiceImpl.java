package com.backend.RestaurantPoll.service.vote;

import com.backend.RestaurantPoll.entity.Restaurant;
import com.backend.RestaurantPoll.entity.User;
import com.backend.RestaurantPoll.entity.Vote;
import com.backend.RestaurantPoll.repository.VoteRepository;
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
        Vote vote = new Vote();
        vote.setRestaurant(restaurant);
        vote.setUser(user);
        voteRepository.save(vote);
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
