package com.UdemyDemo.Udemy.Project.controller;

import com.UdemyDemo.Udemy.Project.dto.UserDto;
import com.UdemyDemo.Udemy.Project.entity.User;
import com.UdemyDemo.Udemy.Project.facade.UserFacade;
import com.UdemyDemo.Udemy.Project.payload.request.LoginRequest;
import com.UdemyDemo.Udemy.Project.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;



@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    private final UserServiceImpl userService;
    private final UserFacade userFacade;


    @GetMapping("/")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        User user = userService.getCurrentUser(principal);
        UserDto userDto = userFacade.getUsertoUserDto(user);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserProfile(@PathVariable("userId") String userId) {
        User user = userService.findById(Long.parseLong(userId));
        UserDto userDto = userFacade.getUsertoUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDto userDto,
                                             Principal principal) {
        User user = userService.update(userDto, principal);
        UserDto userUpdate = userFacade.getUsertoUserDto(user);
        return new ResponseEntity<>(userUpdate, HttpStatus.OK);
    }


    @DeleteMapping("/deleteAccount")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@RequestBody LoginRequest request) {
        userService.deleteAccount(request.getEmail(), request.getPassword());
    }


}
