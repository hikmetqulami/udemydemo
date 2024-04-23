package com.UdemyDemo.Udemy.Project.repository;

import com.UdemyDemo.Udemy.Project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserById(Long id);

    Optional<User> findByUsernameAndPassword(String username, String password);

    Boolean existsByEmail(String email);

}
