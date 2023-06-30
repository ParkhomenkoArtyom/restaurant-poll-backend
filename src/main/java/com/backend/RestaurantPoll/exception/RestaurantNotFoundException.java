package com.backend.RestaurantPoll.exception;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(String message){
        super(message);
    }
}

