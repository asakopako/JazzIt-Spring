package com.rigby.jazzit.repository;

import com.rigby.jazzit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByEmailAndPassword(String email, String password);

}
