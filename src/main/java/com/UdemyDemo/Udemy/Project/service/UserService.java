package com.UdemyDemo.Udemy.Project.service;

import com.UdemyDemo.Udemy.Project.dto.UserDto;
import com.UdemyDemo.Udemy.Project.entity.User;
import com.UdemyDemo.Udemy.Project.payload.request.LoginRequest;
import com.UdemyDemo.Udemy.Project.payload.request.RegisterRequest;

import java.security.Principal;

public interface UserService {

    User findById(Long id);

    void deleteAccount(String email, String password);

    void register(RegisterRequest request);

    String login(LoginRequest request);

    User getUserByEmail(String email);

    User update(UserDto userDto, Principal principal);

}
