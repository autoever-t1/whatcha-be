package com.example.whatcha.global.security.domain;

import com.example.whatcha.domain.user.constant.UserType;
import com.example.whatcha.domain.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth = new ArrayList<>();

        if (this.user.getUserType().equals(UserType.ROLE_ADMIN)) {
            auth.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            auth.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return auth;
    }

    @Override
    public String getPassword() {
        return "";
    }

    public UserType getUserType() {
        return this.user.getUserType();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
