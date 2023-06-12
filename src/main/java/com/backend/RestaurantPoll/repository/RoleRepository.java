package com.backend.RestaurantPoll.repository;

import com.backend.RestaurantPoll.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.code = 'ROLE_ADMIN'")
    Optional<Role> findAdmin();
}
