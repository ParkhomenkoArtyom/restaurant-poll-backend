package com.backend.RestaurantPoll.entity.vote;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/*
 * Entity for connect the vote and
 * the user to know who voted for
 * which restaurant
 */

@Entity
@Table(name = "vote_user")
@Getter
@Setter
public class VoteUser {
    @Id
    @Column(name = "vote_id", nullable = false)
    private Integer voteId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @OneToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "vote_id", nullable = false, referencedColumnName = "id")
    private Vote vote;
}
