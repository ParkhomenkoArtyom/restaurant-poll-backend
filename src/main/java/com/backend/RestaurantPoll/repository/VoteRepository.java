package com.backend.RestaurantPoll.repository;

import com.backend.RestaurantPoll.entity.User;
import com.backend.RestaurantPoll.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Query("select v.user from Vote v where v.restaurant.id = :id")
    Optional<List<User>> getRestaurantVotingUsersIds(@Param("id") Integer id);

    @Modifying
    @Query("DELETE FROM Vote v WHERE v.user.id = :id")
    void remove(@Param("id") Integer id);

    @Modifying
    @Query("DELETE FROM Vote v")
    void removeAll();
}
