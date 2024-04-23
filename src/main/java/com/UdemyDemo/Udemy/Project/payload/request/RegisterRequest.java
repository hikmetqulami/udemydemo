package com.UdemyDemo.Udemy.Project.payload.request;

import com.UdemyDemo.Udemy.Project.annotations.PasswordMatches;
import com.UdemyDemo.Udemy.Project.annotations.ValidEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@PasswordMatches
public class RegisterRequest {

    @Email(message = "It should have email format")
    @NotBlank(message = "User email is required")
    @ValidEmail
    private String email;
    @NotEmpty(message = "Please enter your Username")
    private String username;
    @NotEmpty(message = "Please enter your password")
    @NotEmpty(message = "Password is required")
    @Size(min = 8)
    private String password;
    private String confirmPassword;

}
