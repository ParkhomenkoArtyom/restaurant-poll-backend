package com.backend.RestaurantPoll.entity.vote;

import com.backend.RestaurantPoll.entity.restaurant.Restaurant;
import com.backend.RestaurantPoll.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Table(name = "vote")
@Getter
@Setter
@NoArgsConstructor
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false, referencedColumnName = "id")
    private Restaurant restaurant;

    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinTable(name = "vote_user",
            joinColumns = @JoinColumn(name = "vote_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;

    @OneToOne(mappedBy="vote", cascade=CascadeType.ALL)
    private VoteUser voteUser;

    public Vote(Restaurant restaurant, User user){
        this.restaurant = restaurant;
        this.user = user;
    }
}
