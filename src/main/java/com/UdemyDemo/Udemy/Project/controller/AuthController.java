package com.UdemyDemo.Udemy.Project.controller;

import com.UdemyDemo.Udemy.Project.dto.UserDto;
import com.UdemyDemo.Udemy.Project.facade.UserFacade;
import com.UdemyDemo.Udemy.Project.payload.request.LoginRequest;
import com.UdemyDemo.Udemy.Project.payload.request.RegisterRequest;
import com.UdemyDemo.Udemy.Project.payload.response.JWTTokenSuccessResponse;
import com.UdemyDemo.Udemy.Project.repository.UserRepository;
import com.UdemyDemo.Udemy.Project.service.impl.UserServiceImpl;
import com.UdemyDemo.Udemy.Project.validations.ResponseErrorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class AuthController {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return new ResponseEntity<>("User is taken", HttpStatus.BAD_REQUEST);
        }
        userService.register(request);

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        String token = userService.login(request);
        return ResponseEntity.ok(new JWTTokenSuccessResponse(true, token));
    }




}
