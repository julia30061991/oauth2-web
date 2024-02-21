package com.oauth.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name = "t_user")
public class User implements Serializable {
    @Id
    @Column(name = "user_id", columnDefinition = "INT", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    @Column(name = "name", columnDefinition = "VARCHAR(255)")
    private String name;
    @Column(name = "email", columnDefinition = "VARCHAR(255)")
    private String email;
    @Column(name = "role", columnDefinition = "VARCHAR(255)")
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                '}';
    }
}