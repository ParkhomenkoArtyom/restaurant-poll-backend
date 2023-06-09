package com.backend.RestaurantPoll.controller;

import com.backend.RestaurantPoll.dto.UserDto;
import com.backend.RestaurantPoll.entity.User;
import com.backend.RestaurantPoll.service.role.RoleService;
import com.backend.RestaurantPoll.service.user.UserService;
import com.backend.RestaurantPoll.util.AuthUserUtil;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
public class UserController {
    private final String rolePrefix = "ROLE_";
    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/list")
    public List<UserDto> getUsers() {
        List<User> users = userService.getAllUsers();
        return userService.convertToDto(users);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/add-role")
    public List<UserDto> addUserRole(@RequestParam String role, @RequestParam Integer userId) {
        User user = userService.findById(userId);
        if (Arrays.stream(AuthUserUtil.ROLE.values())
                .anyMatch(element -> Objects.equals(element.get(), rolePrefix + role.toUpperCase()))) {
            roleService.addUserRole(user, rolePrefix + role.toUpperCase());
        }
        return getUsers();
    }
}
