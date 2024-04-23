package com.UdemyDemo.Udemy.Project.repository;

import com.UdemyDemo.Udemy.Project.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LevelRepository extends JpaRepository<Level, Integer> {
    Optional<Level> findByName(String name);

}
