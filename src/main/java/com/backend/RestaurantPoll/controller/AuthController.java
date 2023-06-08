package com.backend.RestaurantPoll.controller;

import ch.qos.logback.core.util.Duration;
import com.backend.RestaurantPoll.jwt.JwtUtil;
import com.backend.RestaurantPoll.dto.UserDto;
import com.backend.RestaurantPoll.service.userDetails.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
class AuthController {
    @Value("${cookies.domain}")
    private String domain;
    private final DaoAuthenticationProvider authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(DaoAuthenticationProvider authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            userDto.getUsername(), userDto.getPassword())
                    );

            UserDetailsImpl user = (UserDetailsImpl) authenticate.getPrincipal();
            user.setPassword(null);

            String token = jwtUtil.generateToken(user);

            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .domain(domain).path("/").maxAge(Duration.buildByDays(365).getMilliseconds()).build();
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(token);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@CookieValue(name = "jwt") String token,
                                           @AuthenticationPrincipal UserDetailsImpl user) {
        try {
            Boolean isValidToken = jwtUtil.validateToken(token, user);
            return ResponseEntity.ok(isValidToken);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .domain(domain).path("/").maxAge(0).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("ok");
    }
}
