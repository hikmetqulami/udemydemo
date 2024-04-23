package com.UdemyDemo.Udemy.Project.facade;

import com.UdemyDemo.Udemy.Project.dto.CourseDto;
import com.UdemyDemo.Udemy.Project.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseFacade {

    public CourseDto courseToCourseDto(Course course) {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setUsername(course.getUser().getUsername());
        courseDto.setUsersLiked(course.getRatingUser());
        courseDto.setTitle(course.getTitle());
        courseDto.setCategory(course.getCategory());
        courseDto.setLanguage(course.getLanguage());
        courseDto.setLevel(course.getLevel());

        return courseDto;
    }
}
