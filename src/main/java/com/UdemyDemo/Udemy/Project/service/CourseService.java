package com.UdemyDemo.Udemy.Project.service;

import com.UdemyDemo.Udemy.Project.dto.CourseDto;
import com.UdemyDemo.Udemy.Project.entity.Course;
import com.UdemyDemo.Udemy.Project.entity.User;

import java.security.Principal;
import java.util.List;

public interface CourseService {


    CourseDto addCourse(CourseDto courseDto, Principal principal);

    User getUserByPrincipal(Principal principal);

    List<CourseDto> getAllCourses();

    Course getCourseById(Long courseId, Principal principal);

    List<CourseDto> getAllCourseForUser(Principal principal);

    CourseDto likeCourse(Long courseId, String username);

    void deleteCourse(Long courseId, Principal principal);
}
