package com.pet.project.weathertracker.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String login;

    @ToString.Exclude
    private String password;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER)
    private List<Location> locations;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
