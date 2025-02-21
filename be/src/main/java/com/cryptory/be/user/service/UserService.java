package com.cryptory.be.user.service;

import com.cryptory.be.user.domain.User;
import com.cryptory.be.user.dto.UserInfoDto;
import com.cryptory.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    // todo: 에러 처리
    public UserInfoDto getUser(String nickname) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        return modelMapper.map(user, UserInfoDto.class);
    }

    @Transactional
    public void deleteUser(String nickname) {
        userRepository.deleteByNickname(nickname);
    }
}
