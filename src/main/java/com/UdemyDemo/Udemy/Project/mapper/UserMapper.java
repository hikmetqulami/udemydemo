package com.UdemyDemo.Udemy.Project.mapper;

import com.UdemyDemo.Udemy.Project.dto.UserDto;
import com.UdemyDemo.Udemy.Project.entity.User;
import com.UdemyDemo.Udemy.Project.payload.request.RegisterRequest;
import com.UdemyDemo.Udemy.Project.security.details.CustomUserDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    User toEntity(UserDto userDto);

    CustomUserDetails toCustomUserDetails(User user);

    User toUserFromRegisterRequest(RegisterRequest request);
}
