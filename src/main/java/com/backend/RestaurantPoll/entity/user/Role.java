package com.backend.RestaurantPoll.entity.user;

import com.backend.RestaurantPoll.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code", nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @Override
    public String getAuthority() {
        return getCode();
    }

    public Role(String code, User user){
        this.code = code;
        this.user = user;
    }
}
