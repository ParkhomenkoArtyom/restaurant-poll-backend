package com.backend.RestaurantPoll.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/*
 * This entity used to select dishes
 * for a new menu if the administrator
 * has not updated the menu during the day.
 */

@Entity
@Table(name = "dish")
@Getter
@Setter
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String dish;

    @Column(nullable = false)
    private Integer price;
}
