package com.cryptory.be.user.service;

import com.cryptory.be.global.util.FileUtils;
import com.cryptory.be.user.domain.User;
import com.cryptory.be.user.dto.UserInfoDto;
import com.cryptory.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private final FileUtils fileUtils;

    private final static String DOMAIN = "http://localhost:8080";
    private final static String IMAGE_PATH = "/attach/files";

    // todo: 에러 처리
    public UserInfoDto getUser(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        return modelMapper.map(user, UserInfoDto.class);
    }

    @Transactional
    public void deleteUser(String userId) {
        userRepository.deleteByUserId(userId);
    }

    @Transactional
    public void updateNickname(String userId, String nickname) {
        log.info("userId: {}", userId);
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        user.updateNickname(nickname);
    }

    @Transactional
    public void updateImage(String userId, MultipartFile file) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        // 우선 게시글 이미지 업로드와 동일하게 처리(위치 동일하게)
        String imageUrl = DOMAIN + IMAGE_PATH + fileUtils.saveFile(file);

        user.updateImage(imageUrl);
    }
}
