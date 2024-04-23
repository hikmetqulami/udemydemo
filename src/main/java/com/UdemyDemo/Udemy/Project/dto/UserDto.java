package com.UdemyDemo.Udemy.Project.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDto {

    private String username;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    private String bio;
}
