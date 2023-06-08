package com.backend.RestaurantPoll.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "menu")
@Getter
@Setter
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 3, max = 20, message = "Не меньше 3 и не больше 20 символов")
    private String dishName;

    @NotNull
    @Column(nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false, referencedColumnName = "id")
    private Restaurant restaurant;
}
