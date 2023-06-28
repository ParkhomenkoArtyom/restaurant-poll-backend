package com.backend.RestaurantPoll.service.user;

import com.backend.RestaurantPoll.controller.dto.request.UserRequestDto;
import com.backend.RestaurantPoll.controller.dto.response.UserResponseDto;
import com.backend.RestaurantPoll.entity.user.Role;
import com.backend.RestaurantPoll.entity.user.User;
import com.backend.RestaurantPoll.exception.UserNotFoundException;
import com.backend.RestaurantPoll.repository.user.UserRepository;
import com.backend.RestaurantPoll.util.AuthUserUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void createAdmin() {
        User admin = new User();
        admin.setName("administrator");
        admin.setRealName("superuser");
        admin.setPassword(bCryptPasswordEncoder.encode("administrator"));

        Role adminRole = new Role();
        adminRole.setCode(AuthUserUtil.ROLE.ADMIN.get());
        adminRole.setUser(admin);
        admin.setRoles(Collections.singleton(adminRole));
        userRepository.save(admin);
    }

    @Override
    public void saveNewUser(UserRequestDto user) {
        User newUser = new User();
        newUser.setName(user.getUsername());
        newUser.setRealName(user.getRealName());
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        newUser.setPassword(encodedPassword);

        Role newUserRole = new Role();
        newUserRole.setCode(AuthUserUtil.ROLE.USER.get());
        newUserRole.setUser(newUser);
        newUser.setRoles(Collections.singleton(newUserRole));

        userRepository.save(newUser);
    }

    @Override
    public User findByName(String username) {
        return userRepository.findByName(username)
                .orElseThrow(()->new UserNotFoundException("User with username: " + username + " not found"));
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User with id: " + id + " not found"));
    }

    @Override
    public Boolean isUserExist(String username) {
        return userRepository.findByName(username).isPresent();
    }

    @Override
    public List<UserResponseDto> convertToDto(List<User> users) {
        return users.stream().map(user -> new UserResponseDto(user.getId(), user.getName(), user.getRealName(), getUserRoles(user)))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getUserRoles(User user) {
        return user.getRoles().stream().map(Role::getCode).collect(Collectors.toList());
    }

    @Override
    public Boolean isVotingUserExist(Integer userId) {
        return userRepository.findUserVote(userId).isPresent();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers().orElseThrow();
    }
}
