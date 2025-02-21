package com.cryptory.be.user.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JwtFilter 실행됨: {}", request.getRequestURI());

        String accessToken = request.getHeader("Authorization");

        log.info("access token: {}", accessToken);

        if(accessToken == null || accessToken.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        accessToken = accessToken.substring(7);

        validateTokens(response, accessToken);

        setAuthentication(accessToken);

        filterChain.doFilter(request, response);
    }

    private void validateTokens(HttpServletResponse response, String accessToken) {
        if(!jwtProvider.validateToken(accessToken)) {
            // refresh token 구현 안 해서 access token 만료시 처리해야됨
            log.debug("Refresh token is expired");
        }
    }

    private void setAuthentication(String accessToken) {
        Authentication authentication = jwtProvider.getAuthentication(accessToken);

        log.info("최종 SecurityContext Authentication: {}", authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
