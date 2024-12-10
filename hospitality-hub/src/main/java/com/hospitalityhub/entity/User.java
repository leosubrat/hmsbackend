package com.hospitalityhub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
    private String forgetPasswordCode;

    @Temporal(TemporalType.TIMESTAMP)
    private Date forgetPasswordCodeTimestamp;

    @Enumerated(EnumType.STRING)
    private Role role;

    public String getRole(){
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
