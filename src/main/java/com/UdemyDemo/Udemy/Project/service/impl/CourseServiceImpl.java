package com.UdemyDemo.Udemy.Project.service.impl;

import com.UdemyDemo.Udemy.Project.dto.CourseDto;
import com.UdemyDemo.Udemy.Project.entity.Course;
import com.UdemyDemo.Udemy.Project.entity.User;
import com.UdemyDemo.Udemy.Project.entity.VideoModel;
import com.UdemyDemo.Udemy.Project.exception.NotFoundException;
import com.UdemyDemo.Udemy.Project.mapper.CourseMapper;
import com.UdemyDemo.Udemy.Project.repository.CourseRepository;
import com.UdemyDemo.Udemy.Project.repository.UserRepository;
import com.UdemyDemo.Udemy.Project.repository.VideoModelRepository;
import com.UdemyDemo.Udemy.Project.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final VideoModelRepository videoModelRepository;
    private final CourseMapper courseMapper;

    public Course addCourse(CourseDto courseDto, Principal principal) {
        User user = getUserByPrincipal(principal);
        Course course = new Course();
        course.setUser(user);
        course.setTitle(courseDto.getTitle());
        course.setCategory(courseDto.getCategory());
        course.setLevel(courseDto.getLevel());
        course.setLanguage(courseDto.getLanguage());
        log.info("Saving course for User {}", user.getUsername());
        return courseRepository.save(course);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found with username" + username));
    }


    public List<Course> getAllCourses() {
        return courseRepository.findAllByOrderByCreatedDateDesc();
    }

    public Course getCourseById(Long courseId, Principal principal) {
        User user = getUserByPrincipal(principal);
        return courseRepository.findByIdAndUser(courseId, user)
                .orElseThrow(() -> new NotFoundException("Course not found for username" + user.getEmail()));
    }

    public List<Course> getAllCourseForUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        return courseRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    public Course likeCourse(Long courseId, String username) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found for username"));

        //TODO LIKE CONFIG ETMEYI UNUTMA;
        return courseRepository.save(course);
    }

    public void deleteCourse(Long courseId, Principal principal) {
        Course course = getCourseById(courseId, principal);
        Optional<VideoModel> videoModel = videoModelRepository.findById(course.getId());
        courseRepository.delete(course);
        videoModel.ifPresent(videoModelRepository::delete);

    }

}
