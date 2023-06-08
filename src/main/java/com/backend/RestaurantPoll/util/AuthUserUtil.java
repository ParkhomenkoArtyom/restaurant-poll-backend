package com.backend.RestaurantPoll.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUserUtil {

    public enum ROLE {
        ADMIN("ROLE_ADMIN"),
        USER("ROLE_USER");
        private final String type;

        ROLE(String type) {
            this.type = type;
        }

        public String get() {
            return type;
        }
    }

    //Returns the username of user authorized in the system
    public static String getAuthenticationUsername() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }
}
