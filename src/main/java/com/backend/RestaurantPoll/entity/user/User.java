package com.backend.RestaurantPoll.entity.user;

import com.backend.RestaurantPoll.entity.vote.Vote;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size(min = 5, max = 20, message = "At least 5 and no more 20 characters")
    private String name;

    @NotEmpty
    @Size(min = 5, max = 20, message = "At least 5 and no more 20 characters")
    private String realName;

    @NotEmpty
    @Size(min = 8, message = "At least 5 characters")
    private String password;

    @OneToMany(mappedBy="user", cascade=CascadeType.PERSIST)
    private Set<Role> roles = new LinkedHashSet<>();

    @OneToOne(cascade=CascadeType.ALL)
    @JoinTable(name = "vote_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "vote_id"))
    private Vote vote;

    public User(String name, String realName, String password, Set<Role> roles){
        this.name = name;
        this.realName = realName;
        this.password = password;
        this.roles = roles;
    }
}
