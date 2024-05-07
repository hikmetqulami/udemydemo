package com.UdemyDemo.Udemy.Project.service.impl;

import com.UdemyDemo.Udemy.Project.dto.CourseDto;
import com.UdemyDemo.Udemy.Project.entity.*;
import com.UdemyDemo.Udemy.Project.exception.NotFoundException;
import com.UdemyDemo.Udemy.Project.mapper.CourseMapper;
import com.UdemyDemo.Udemy.Project.repository.*;
import com.UdemyDemo.Udemy.Project.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final VideoModelRepository videoModelRepository;
    private final CategoryRepository categoryRepository;
    private final LevelRepository levelRepository;
    private final LanguageRepository languageRepository;
    private final CourseMapper courseMapper;

    @Override
    public CourseDto addCourse(CourseDto courseDto, Principal principal) {
        User user = getUserByPrincipal(principal);
        Course course = courseMapper.toEntity(courseDto);
        course.setUser(user);
        course.setTitle(courseDto.getTitle());

        Category category = categoryRepository.findByName(courseDto.getCategoryName())
                .orElseThrow(() -> new NotFoundException("Category not found"));
        course.setCategory(category);

        Level level = levelRepository.findByName(courseDto.getLevelName())
                .orElseThrow(() -> new NotFoundException("Level not found"));
        course.setLevel(level);

        Language language = languageRepository.findByName(courseDto.getLanguageName())
                .orElseThrow(() -> new NotFoundException("Language not found"));
        course.setLanguage(language);


        log.info("Saving course for User {}", user.getUsername());

        Course savedCourse = courseRepository.save(course);
        return courseMapper.toDto(savedCourse);
    }

    @Override
    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found with username" + username));
    }

    @Override
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAllByOrderByCreatedDateDesc()
                .stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Course getCourseById(Long courseId, Principal principal) {
        User user = getUserByPrincipal(principal);
        return courseRepository.findByIdAndUser(courseId, user)
                .orElseThrow(() -> new NotFoundException("Course not found for username" + user.getEmail()));
    }

    @Override
    public List<CourseDto> getAllCourseForUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        return courseRepository.findAllByUserOrderByCreatedDateDesc(user)
                .stream()
                .map(courseMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CourseDto likeCourse(Long courseId, String username) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found for username"));

        Optional<String> userLiked = course.getLikedUsers()
                .stream()
                .filter(u -> u.equals(username)).findAny();

        if (userLiked.isPresent()) {
            course.setLikes(course.getLikes() - 1);
            course.getLikedUsers().remove(username);
        } else {
            course.setLikes(course.getLikes() + 1);
            course.getLikedUsers().add(username);
        }

        Course savedCourse = courseRepository.save(course);
        return courseMapper.toDto(savedCourse);
    }

    @Override
    public void deleteCourse(Long courseId, Principal principal) {
        Course course = getCourseById(courseId, principal);
        Optional<VideoModel> videoModel = videoModelRepository.findById(course.getId());
        courseRepository.delete(course);
        videoModel.ifPresent(videoModelRepository::delete);
    }

}
