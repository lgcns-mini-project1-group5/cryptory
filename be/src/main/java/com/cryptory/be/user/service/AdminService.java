package com.cryptory.be.user.service;

import com.cryptory.be.user.domain.User;
import com.cryptory.be.user.dto.AdminAuthDto;
import com.cryptory.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public void adminSignup(AdminAuthDto signupDto) {
        Optional<User> admin = userRepository.findByUserId(signupDto.getUserId());

        if (admin.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 관리자 계정입니다.");
        }

        userRepository.save(User.createAdminUser(
                signupDto.getUserId(),
                bCryptPasswordEncoder.encode(signupDto.getPassword())
        ));
    }
}
