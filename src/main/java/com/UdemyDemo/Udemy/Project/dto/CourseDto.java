package com.UdemyDemo.Udemy.Project.dto;

import com.UdemyDemo.Udemy.Project.entity.Category;
import com.UdemyDemo.Udemy.Project.entity.Language;
import com.UdemyDemo.Udemy.Project.entity.Level;
import lombok.Data;

import java.util.Set;

@Data
public class CourseDto {

    private Long id;
    private String title;
    private String username;
    private Category category;
    private Level level;
    private Language language;
    private Long rating;
    private Set<String> usersLiked;
}
