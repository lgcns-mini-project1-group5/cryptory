package com.cryptory.be.admin.service;

import com.cryptory.be.admin.dto.admin.AdminCreateRequestDto;
import com.cryptory.be.admin.dto.admin.AdminListResponseDto;
import com.cryptory.be.admin.dto.user.UserBlockRequestDto;
import com.cryptory.be.admin.dto.user.UserListResponseDto;
import com.cryptory.be.user.domain.User;
import com.cryptory.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.cryptory.be.admin.service
 * fileName       : AdminUserServiceImpl
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserServiceImpl implements AdminUserService{
    private final UserRepository userRepository;

    @Override
    public Page<UserListResponseDto> getUserList(String keyword, int page, int size, String sort) {
        Sort sorting = parseSort(sort);
        Pageable pageable = PageRequest.of(page, size, sorting);
        Page<User> users;

        if (keyword == null || keyword.isBlank()) {
            users = userRepository.findAll(pageable); // 모든 사용자 조회 (페이징)
        } else {
            users = userRepository.findByNicknameContaining(keyword, pageable); // 닉네임 검색 (페이징)
        }

        return users.map(this::convertToUserListResponseDto);
    }

    @Override
    public void blockUser(Integer userId, UserBlockRequestDto requestDto) {

    }

    @Override
    public Page<AdminListResponseDto> getAdminList(int page, int size, String sort) {
        return null;
    }

    @Override
    public void blockAdmin(Integer userId, UserBlockRequestDto requestDto) {

    }

    @Override
    public Integer createAdmin(AdminCreateRequestDto requestDto) {
        return 0;
    }
}
