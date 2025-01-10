package com.example.whatcha.global.security.service;

import com.example.whatcha.domain.user.constant.UserExceptionMessage;
import com.example.whatcha.domain.user.dao.UserRepository;
import com.example.whatcha.domain.user.domain.User;
import com.example.whatcha.domain.user.exception.UserNotFoundException;
import com.example.whatcha.global.security.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(
                ()->new UserNotFoundException(UserExceptionMessage.USER_NOT_FOUND.getMessage()));
        return new CustomUserDetails(user);
    }
}
