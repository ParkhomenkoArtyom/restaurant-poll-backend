package com.backend.RestaurantPoll.service.user.role;

import com.backend.RestaurantPoll.entity.user.Role;
import com.backend.RestaurantPoll.entity.user.User;
import com.backend.RestaurantPoll.repository.user.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void addUserRole(User user, String userRole) {
        roleRepository.save(new Role(userRole, user));
    }

    @Override
    public Boolean ifAdminExist() {
        return roleRepository.findAdmin().isPresent();
    }
}
