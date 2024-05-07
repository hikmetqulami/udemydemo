package com.UdemyDemo.Udemy.Project.controller;

import com.UdemyDemo.Udemy.Project.dto.CourseDto;
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

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseServiceImpl courseService;


    @PostMapping("/create")
    public ResponseEntity<CourseDto> createCourse(@Valid @RequestBody CourseDto courseDto,
                                               BindingResult bindingResult,
                                               Principal principal) {
        CourseDto course = courseService.addCourse(courseDto, principal);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CourseDto>> getAllCourses(Principal principal) {
        List<CourseDto> courseList = courseService.getAllCourses();
        return new ResponseEntity<>(courseList, HttpStatus.OK);
    }

    @GetMapping("/{courseId}/{username}/like")
    public ResponseEntity<CourseDto> getAllCoursesForUser(@PathVariable("courseId") String postId,
                                                                @PathVariable("username") String username) {
      CourseDto course = courseService.likeCourse(Long.parseLong(postId), username);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}/delete")
    public ResponseEntity<MessageResponse> deleteCourse(@PathVariable("courseId") String postId,
                                                        Principal principal) {
        courseService.deleteCourse(Long.parseLong(postId), principal);
        return new ResponseEntity<>(new MessageResponse("Course deleted"), HttpStatus.OK);
    }

}
