package com.hospitalityhub.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Builder
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
    private String forgetPasswordCode;

    @Temporal(TemporalType.TIMESTAMP)
    private Date forgetPasswordCodeTimestamp;

    @Enumerated(EnumType.STRING)
    private Role role;

    public String getRole() {
        return role.name();
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return new ArrayList<>() {{
            add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return role.name();
                }
            });
        }};
    }

}
