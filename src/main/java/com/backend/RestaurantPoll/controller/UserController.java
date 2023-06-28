package com.backend.RestaurantPoll.controller;

import com.backend.RestaurantPoll.controller.dto.response.UserResponseDto;
import com.backend.RestaurantPoll.entity.user.User;
import com.backend.RestaurantPoll.service.user.role.RoleService;
import com.backend.RestaurantPoll.service.user.UserService;
import com.backend.RestaurantPoll.util.AuthUserUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/list")
    public List<UserResponseDto> getUsers() {
        List<User> users = userService.getAllUsers();
        return userService.convertToDto(users);
    }
    @GetMapping("/add-role")
    public List<UserResponseDto> addUserRole(@RequestParam String role, @RequestParam Integer userId) {
        User user = userService.findById(userId);
        if (Arrays.stream(AuthUserUtil.ROLE.values())
                .anyMatch(element -> Objects.equals(element.get(), rolePrefix + role.toUpperCase()))) {
            roleService.addUserRole(user, rolePrefix + role.toUpperCase());
        }
        return getUsers();
    }

    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin() {
        if (roleService.ifAdminExist())
            return ResponseEntity.ok("Admin is already exist");

        userService.createAdmin();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
