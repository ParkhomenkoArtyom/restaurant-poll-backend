package com.backend.RestaurantPoll.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 3, max = 20, message = "Не меньше 3 и не больше 20 символов")
    private String name;

    @OneToMany(mappedBy="restaurant", cascade=CascadeType.ALL)
    private Set<Menu> menu = new LinkedHashSet<>();

    @OneToMany(mappedBy="restaurant", cascade=CascadeType.ALL)
    private Set<Vote> votes = new LinkedHashSet<>();
}
