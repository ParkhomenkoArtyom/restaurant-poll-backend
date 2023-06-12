package com.backend.RestaurantPoll.service.user;

import com.backend.RestaurantPoll.dto.UserDto;
import com.backend.RestaurantPoll.entity.Role;
import com.backend.RestaurantPoll.entity.User;
import com.backend.RestaurantPoll.repository.UserRepository;
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
        adminRole.setCode("ROLE_ADMIN");
        adminRole.setUser(admin);
        admin.setRoles(Collections.singleton(adminRole));
        userRepository.save(admin);
    }

    @Override
    public void saveNewUser(UserDto user) {
        User newUser = new User();
        newUser.setName(user.getUsername());
        newUser.setRealName(user.getRealName());
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        newUser.setPassword(encodedPassword);

        Role newUserRole = new Role();
        newUserRole.setCode("ROLE_USER");
        newUserRole.setUser(newUser);
        newUser.setRoles(Collections.singleton(newUserRole));

        userRepository.save(newUser);
    }

    @Override
    public User findByName(String username) {
        return userRepository.findByName(username).orElseThrow();
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public Boolean isUserExist(String username) {
        return userRepository.findByName(username).isPresent();
    }

    @Override
    public List<UserDto> convertToDto(List<User> users) {
        return users.stream().map(user -> new UserDto(user.getId(), user.getName(), user.getRealName(), getUserRoles(user)))
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
