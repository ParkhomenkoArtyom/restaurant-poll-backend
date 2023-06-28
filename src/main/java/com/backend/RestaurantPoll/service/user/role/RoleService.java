package com.backend.RestaurantPoll.service.user.role;

import com.backend.RestaurantPoll.entity.user.User;

public interface RoleService {
    void addUserRole(User user, String userRole);
    Boolean ifAdminExist();
}
