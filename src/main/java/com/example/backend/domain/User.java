package com.example.backend.domain;
//test
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(
            name = "users_seq",
            sequenceName = "users_seq",
            allocationSize = 1
    )
    private long id;

    private String email;
    private String password;
    private String name;
    @Column(name="phone")
    private String phone;

    public User (String email , String password , String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

}
