package com.example.backend.admin.domain;

import com.example.backend.admin.dto.user.UpdateStatusRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_block_history")
public class UserBlockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name="admin_id")
    private Long adminId;
    @Column(name = "reason")
    private String reason;
    @Column(name = "blocked_at")
    @CreationTimestamp
    private LocalDateTime blockedAt;
    @Column(name = "unblocked_at")
    private LocalDateTime unblockedAt;

    public static UserBlockHistory create (UpdateStatusRequest req) {
        UserBlockHistory userBlockHistory = new UserBlockHistory();
        userBlockHistory.setUserId(req.userId());
        userBlockHistory.setAdminId(req.adminId());
        userBlockHistory.setReason(req.reason());
        return userBlockHistory;
    }
};
