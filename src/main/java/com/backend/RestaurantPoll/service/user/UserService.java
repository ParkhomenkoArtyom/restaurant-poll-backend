package com.backend.RestaurantPoll.service.user;

import com.backend.RestaurantPoll.controller.dto.request.UserRequestDto;
import com.backend.RestaurantPoll.controller.dto.response.UserResponseDto;
import com.backend.RestaurantPoll.entity.user.User;
import java.util.List;

public interface UserService {
    void createAdmin();
    void saveNewUser(UserRequestDto user);

    User findByName(String username);

    User findById(Integer id);

    Boolean isUserExist(String username);

    List<UserResponseDto> convertToDto(List<User> users);

    List<User> getAllUsers();

    List<String> getUserRoles(User user);

    Boolean isVotingUserExist(Integer userId);
}
