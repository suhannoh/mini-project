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
        h.reason, h.blockedAt,
        (SELECT count(hh)
         FROM UserBlockHistory hh
         where hh.userId = u.id
           )
         )
    from User u
    left join UserBlockHistory h 
        ON h.userId = u.id and h.unblockedAt is null    
    """)
    Page<FindUsersResponse> findAllUsersWithBlockInfo (Pageable pageable);
}
