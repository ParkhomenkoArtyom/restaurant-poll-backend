package com.backend.RestaurantPoll.repository;

import com.backend.RestaurantPoll.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
