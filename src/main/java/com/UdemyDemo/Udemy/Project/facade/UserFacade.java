package com.UdemyDemo.Udemy.Project.facade;

import com.UdemyDemo.Udemy.Project.dto.UserDto;
import com.UdemyDemo.Udemy.Project.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {

    public UserDto getUsertoUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setBio(user.getBio());
        return userDto;
    }
}
