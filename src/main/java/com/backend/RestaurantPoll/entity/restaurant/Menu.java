package com.backend.RestaurantPoll.entity.restaurant;

import com.backend.RestaurantPoll.entity.restaurant.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "menu")
@Getter
@Setter
@NoArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 3, max = 20, message = "At least 3 and no more 20 characters")
    private String dishName;

    @NotNull
    @Column(nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false, referencedColumnName = "id")
    private Restaurant restaurant;

    public Menu(String dishName, Integer price, Restaurant restaurant){
        this.dishName = dishName;
        this.price = price;
        this.restaurant = restaurant;
    }
}
