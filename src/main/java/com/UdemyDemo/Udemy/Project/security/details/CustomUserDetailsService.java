package com.UdemyDemo.Udemy.Project.security.details;

import com.UdemyDemo.Udemy.Project.mapper.UserMapper;
import com.UdemyDemo.Udemy.Project.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMapper.toCustomUserDetails(userRepository.findUserByEmail(username).get());
    }
}
