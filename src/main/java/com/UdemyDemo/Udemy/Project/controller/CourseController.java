package com.UdemyDemo.Udemy.Project.controller;

import com.UdemyDemo.Udemy.Project.dto.CourseDto;
import com.UdemyDemo.Udemy.Project.entity.Course;
import com.UdemyDemo.Udemy.Project.facade.CourseFacade;
import com.UdemyDemo.Udemy.Project.payload.response.MessageResponse;
import com.UdemyDemo.Udemy.Project.service.impl.CourseServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseServiceImpl courseService;
    private final CourseFacade courseFacade;


    @PostMapping("/create")
    public ResponseEntity<CourseDto> createCourse(@Valid @RequestBody CourseDto courseDto,
                                               BindingResult bindingResult,
                                               Principal principal) {
        Course course = courseService.addCourse(courseDto, principal);
        CourseDto createCourse = courseFacade.courseToCourseDto(course);

        return new ResponseEntity<>(createCourse, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CourseDto>> getAllCourses(Principal principal) {
        List<CourseDto> courseDtoList = courseService.getAllCourses()
                .stream()
                .map(courseFacade::courseToCourseDto)
                .collect(Collectors.toUnmodifiableList());
        return new ResponseEntity<>(courseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{courseId}/{username}/like")
    public ResponseEntity<CourseDto> getAllCoursesForUser(@PathVariable("courseId") String postId,
                                                                @PathVariable("username") String username) {
      Course course = courseService.likeCourse(Long.parseLong(postId), username);
      CourseDto courseDto = courseFacade.courseToCourseDto(course);

        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}/delete")
    public ResponseEntity<MessageResponse> deleteCourse(@PathVariable("courseId") String postId,
                                                        Principal principal) {
        courseService.deleteCourse(Long.parseLong(postId), principal);
        return new ResponseEntity<>(new MessageResponse("Course deleted"), HttpStatus.OK);
    }

}
