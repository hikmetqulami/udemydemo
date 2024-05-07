package com.UdemyDemo.Udemy.Project.controller;

import com.UdemyDemo.Udemy.Project.dto.UserDto;
import com.UdemyDemo.Udemy.Project.entity.User;
import com.UdemyDemo.Udemy.Project.payload.request.LoginRequest;
import com.UdemyDemo.Udemy.Project.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;



@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        User user = userService.getCurrentUser(principal);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserProfile(@PathVariable("userId") String userId) {
        UserDto user = userService.findById(Long.parseLong(userId));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDto userDto,
                                             Principal principal) {
        UserDto user = userService.update(userDto, principal);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @DeleteMapping("/deleteAccount")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@RequestBody LoginRequest request) {
        userService.deleteAccount(request.getEmail(), request.getPassword());
    }


}
