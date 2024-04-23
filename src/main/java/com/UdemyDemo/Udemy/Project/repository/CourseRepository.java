package com.UdemyDemo.Udemy.Project.repository;

import com.UdemyDemo.Udemy.Project.entity.Course;
import com.UdemyDemo.Udemy.Project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {


    List<Course> findAllByUserOrderByCreatedDateDesc(User user);

    List<Course> findAllByOrderByCreatedDateDesc();

    Optional<Course> findByIdAndUser(Long id, User user);

}
