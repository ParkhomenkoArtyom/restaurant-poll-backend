package com.backend.RestaurantPoll.controller;

import com.backend.RestaurantPoll.jwt.JwtUtil;
import com.backend.RestaurantPoll.dto.UserDto;
import com.backend.RestaurantPoll.service.user.UserService;
import com.backend.RestaurantPoll.service.userDetails.UserDetailsImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
@RequestMapping("/api")
public class RegistrationController {

    private final UserService userService;
    private final DaoAuthenticationProvider authenticationManager;
    private final JwtUtil jwtUtil;

    public RegistrationController(UserService userService, DaoAuthenticationProvider authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        if (!userService.isUserExist(userDto.getUsername())) {
            userService.saveNewUser(userDto);
            try {
                Authentication authenticate = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userDto.getUsername(), userDto.getPassword()
                        )
                );

                UserDetailsImpl user = (UserDetailsImpl) authenticate.getPrincipal();
                user.setPassword(null);
                return ResponseEntity.ok()
                        .header(
                                HttpHeaders.AUTHORIZATION,
                                jwtUtil.generateToken(user)
                        )
                        .body(user);
            } catch (BadCredentialsException ex) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
