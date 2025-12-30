package com.example.backend.user.repository;

import com.example.backend.admin.dto.user.FindUsersResponse;
import com.example.backend.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail (String email);
    Optional<User> findUserById (long id);
    Optional<User> findByEmailAndName (String email, String name);
    boolean existsByEmail(String email);

    @Query("""
    select new com.example.backend.admin.dto.user.FindUsersResponse(
        u.id , u.email, u.name, u.phone, u.gender , u.role ,
        u.createdAt, u.updatedAt , u.lastLoginAt , u.status,
        (select ub.reason
            from UserBlockHistory ub
            where ub.userId = u.id
               and ub.unblockedAt is null),
        (select ub.blockedAt
             from UserBlockHistory ub
             where ub.userId = u.id
               and ub.unblockedAt is null),
        (select count(ub)
            from UserBlockHistory ub
            where ub.userId = u.id )
        )
    from User u           
    """)
    Page<FindUsersResponse> findAllUsersWithBlockInfo (Pageable pageable);
}
