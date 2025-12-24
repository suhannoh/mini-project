package com.example.backend.user_active.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_active")
@Getter
@Setter
@NoArgsConstructor
public class UserActive {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "logged_in_at", nullable = false)
    private LocalDateTime loggedInAt;

    public UserActive (Long userId , String userName , LocalDateTime loggedInAt) {
        this.userId = userId;
        this.userName = userName;
        this.loggedInAt = loggedInAt;
    }
}

