package com.backend.RestaurantPoll.service.role;

import com.backend.RestaurantPoll.entity.User;

public interface RoleService {
    void addUserRole(User user, String userRole);
}
