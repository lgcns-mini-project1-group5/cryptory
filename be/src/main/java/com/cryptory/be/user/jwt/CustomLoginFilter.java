package com.cryptory.be.user.jwt;

import com.cryptory.be.admin.dto.admin.AdminLoginRequest;
import com.cryptory.be.admin.dto.admin.AdminLoginResponse;
import com.cryptory.be.user.domain.User;
import com.cryptory.be.user.dto.PrincipalUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomLoginFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
        setFilterProcessesUrl("/admin/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            // 요청 본문을 AdminLoginRequest 객체로 파싱
            AdminLoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), AdminLoginRequest.class);

            String userId = loginRequest.getUserId();
            String password = loginRequest.getPassword();


            log.info("userId: {}, password: {}", userId, password); //여기 로그가 null로 출력되는지 확인

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userId, password, null);

            return getAuthenticationManager().authenticate(authenticationToken);

        } catch (IOException e) {
            // JSON 파싱 오류 처리.  로그를 남기고, 예외를 다시 던지거나, 적절한 응답을 반환.
            log.error("Error parsing login request body: {}", e.getMessage());
            throw new RuntimeException("Failed to parse login request body", e); // 또는 Bad Request 응답
        }


    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String accessToken = jwtProvider.generateAccessToken(authResult);

        PrincipalUserDetails principalUserDetails = (PrincipalUserDetails) authResult.getPrincipal();
        User user = principalUserDetails.getUser();

        AdminLoginResponse adminLoginResponse = new AdminLoginResponse(user.getId(), user.getUserId(), user.getNickname());
        String responseJson = objectMapper.writeValueAsString(adminLoginResponse);

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(responseJson);

        log.info("생성된 access token: {}", accessToken);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("로그인 실패");
    }
}
