package com.UdemyDemo.Udemy.Project.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CourseDto {

    private String title;
    private String username;
    private String languageName;
    private String levelName;
    private String categoryName;
    private Set<String> usersLiked;
}
