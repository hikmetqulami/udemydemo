package com.UdemyDemo.Udemy.Project.mapper;

import com.UdemyDemo.Udemy.Project.dto.CourseDto;
import com.UdemyDemo.Udemy.Project.entity.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    Course toEntity(CourseDto courseDto);

    CourseDto toDto(Course course);
}
