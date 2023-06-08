package com.backend.RestaurantPoll.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size(min = 5, max = 20, message = "Не меньше 5 и не больше 20 символов")
    private String name;

    @NotEmpty
    @Size(min = 5, max = 20, message = "Не меньше 5 и не больше 20 символов")
    private String realName;

    @NotEmpty
    @Size(min = 8, message = "Не меньше 8 ")
    private String password;

    @OneToMany(mappedBy="user", cascade=CascadeType.PERSIST)
    private Set<Role> roles = new LinkedHashSet<>();

    @OneToOne(cascade=CascadeType.ALL)
    @JoinTable(name = "vote_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "vote_id"))
    private Vote vote;
}
