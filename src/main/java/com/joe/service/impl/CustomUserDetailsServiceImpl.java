package com.joe.service.impl;

import com.joe.model.User;
import com.joe.repository.UserRepository;
import com.joe.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        UserDetails userDetails;
        User user = userRepository.findByEmail(userName);

        if (user != null){
            List<GrantedAuthority> authorities = new ArrayList<>();
            userDetails = new org.springframework.security.core.userdetails.User(
                    user.getEmail(), user.getPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
        return userDetails;
    }
}
