package com.backend.RestaurantPoll.service.role;

import com.backend.RestaurantPoll.entity.Role;
import com.backend.RestaurantPoll.entity.User;
import com.backend.RestaurantPoll.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void addUserRole(User user, String userRole) {
        Role role = new Role();
        role.setUser(user);
        role.setCode(userRole);
        roleRepository.save(role);
    }
}
