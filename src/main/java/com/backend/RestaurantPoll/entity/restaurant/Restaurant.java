package com.backend.RestaurantPoll.entity.restaurant;

import com.backend.RestaurantPoll.entity.vote.Vote;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 3, max = 20, message = "At least 3 and no more 20 characters")
    private String name;

    @OneToMany(mappedBy="restaurant", cascade=CascadeType.ALL)
    private Set<Menu> menu = new LinkedHashSet<>();

    @OneToMany(mappedBy="restaurant", cascade=CascadeType.ALL)
    private Set<Vote> votes = new LinkedHashSet<>();

    public Restaurant(Integer id, String name, Set<Menu> menu) {
        this.id = id;
        this.name = name;
        this.menu = menu;
    }
}
