package com.cryptory.be.user.dto;

import com.cryptory.be.user.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class PrincipalUserDetails implements OAuth2User, UserDetails {

    private final User user;
    private Map<String, Object> attributes;

    // 일반 로그인 때문에
    public PrincipalUserDetails(User user) {
        this.user = user;
    }

    // 소셜 로그인
    public PrincipalUserDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    // SimpleGrantedAuthority는 GrantedAuthority 인터페이스의 구현체
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // ROLE 여러개인 경우 바꿔주기
        // 현재는 ROLE_USER, ROLE_ADMIN 하나씩만 존재
        return List.of(new SimpleGrantedAuthority(user.getRole().getValue()));
    }


    // OAuth2User 메소드
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // AuthenticationPrincipal 상속, OAuth2 서비스의 주된 식별자 반환
    @Override
    public String getName() {
        return "";
//        return user.getName();
    }


    // UserDetails 메소드들 - 기본 인증, 인가에 필요
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

//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }

    @Override
    public String getPassword() {
        return "";
    }

    // 기본 인증, 인가에 필요한 주된 식별자 반환
    @Override
    public String getUsername() {
        return user.getNickname();
    }
}

