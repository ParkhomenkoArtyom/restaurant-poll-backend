package com.backend.RestaurantPoll.service.user;

import com.backend.RestaurantPoll.dto.UserDto;
import com.backend.RestaurantPoll.entity.User;
import java.util.List;

public interface UserService {
    void createAdmin();
    void saveNewUser(UserDto user);

    User findByName(String username);

    User findById(Integer id);

    Boolean isUserExist(String username);

    List<UserDto> convertToDto(List<User> users);

    List<User> getAllUsers();

    List<String> getUserRoles(User user);

    Boolean isVotingUserExist(Integer userId);
}
