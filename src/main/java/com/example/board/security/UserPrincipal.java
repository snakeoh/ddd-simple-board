package com.example.board.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {

    private final String username;
    private final String password; // 비밀번호 필요 없으면 null 처리 가능
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String username) {
        this.username = username;
        this.password = null;
        this.authorities = null;
    }

    public UserPrincipal(String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities; // 권한 미사용 시 null 또는 빈 리스트 반환
    }

    @Override
    public String getPassword() {
        return password; // 비밀번호 미사용 시 null
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
