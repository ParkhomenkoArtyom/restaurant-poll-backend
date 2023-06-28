package com.backend.RestaurantPoll.repository.user;

import com.backend.RestaurantPoll.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);

    Optional<User> findById(Integer id);

    @Query("SELECT u FROM User u")
    Optional<List<User>> getAllUsers();

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.code LIKE 'ROLE_USER'")
    Optional<List<User>> getAllUsersExceptAdmins();

    @Query("select u.vote from User u where u.vote.voteUser.userId = :id")
    Optional<User> findUserVote(@Param("id") Integer id);
}
