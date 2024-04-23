package com.UdemyDemo.Udemy.Project.service.impl;

import com.UdemyDemo.Udemy.Project.dto.UserDto;
import com.UdemyDemo.Udemy.Project.entity.Role;
import com.UdemyDemo.Udemy.Project.entity.User;
import com.UdemyDemo.Udemy.Project.exception.NotFoundException;
import com.UdemyDemo.Udemy.Project.mapper.UserMapper;
import com.UdemyDemo.Udemy.Project.payload.request.LoginRequest;
import com.UdemyDemo.Udemy.Project.payload.request.RegisterRequest;
import com.UdemyDemo.Udemy.Project.repository.RoleRepository;
import com.UdemyDemo.Udemy.Project.repository.UserRepository;
import com.UdemyDemo.Udemy.Project.security.config.SecurityConstants;
import com.UdemyDemo.Udemy.Project.security.jwt.JWTTokenProvider;
import com.UdemyDemo.Udemy.Project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider provider;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public void deleteAccount(String email, String password) {
        User user = userRepository.findUserByEmail(email).get();
        if (user == null) {
            throw new NotFoundException("User not found with email: " + email);
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new NotFoundException("Incorrect password");
        }
        user.getRoleList().clear();
        user.getCourses().clear();
        userRepository.delete(user);
    }

    @Override
    public void register(RegisterRequest request) {
        User user = userMapper.toUserFromRegisterRequest(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role roles = roleRepository.findByName("USER").orElseThrow(() ->
                new NotFoundException("Default role not found"));
        user.setRoleList(Collections.singletonList(roles));
        userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .stream().findFirst()
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public String login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        UserDetails user = userMapper.toCustomUserDetails(getUserByEmail(request.getEmail()));
        String token = SecurityConstants.TOKEN_PREFIX + provider.generateToken(user);
        return token;
    }



    public User update(UserDto userDto, Principal principal) {
        User user = getUserByPrincipal(principal);
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setBio(userDto.getBio());
        return userRepository.save(user);
    }

    public User getCurrentUser(Principal principal) {
        return getUserByEmail(principal.getName());
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found with username" + username));
    }


}
