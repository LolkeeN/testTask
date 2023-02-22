package com.vasyl.testTask.security;

import com.vasyl.testTask.entity.User;
import com.vasyl.testTask.exceptions.UserNotFoundException;
import com.vasyl.testTask.repository.UserRepository;
import com.vasyl.testTask.security.userdetails.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new UserNotFoundException("No user found with such username"));
        return new CustomUserDetails(user);
    }

}
