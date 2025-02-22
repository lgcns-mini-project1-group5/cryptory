package com.cryptory.be.admin.controller;

import com.cryptory.be.admin.dto.admin.AdminCreateRequestDto;
import com.cryptory.be.admin.dto.admin.AdminListResponseDto;
import com.cryptory.be.admin.dto.user.UserBlockRequestDto;
import com.cryptory.be.admin.dto.user.UserListResponseDto;
import com.cryptory.be.admin.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * packageName    : com.cryptory.be.admin.controller
 * fileName       : AdminUserController
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */
@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final AdminUserService adminUserService;

    // 일반 회원 목록 조회
    @GetMapping
    public ResponseEntity<Page<UserListResponseDto>> getUserList(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort){

        Page<UserListResponseDto> users = adminUserService.getUserList(keyword, page, size, sort);
        return ResponseEntity.ok(users);
    }

    // 일반 회원 차단/차단해제
    @PatchMapping("/{userId}/block")
    public ResponseEntity<Void> blockUser(@PathVariable Integer userId, @RequestBody UserBlockRequestDto requestDto ){
        adminUserService.blockUser(userId, requestDto);
        return ResponseEntity.ok().build();
    }

    // 관리자 목록 조회
    @GetMapping("/admins")
    public ResponseEntity<Page<AdminListResponseDto>> getAdminList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort) {

        Page<AdminListResponseDto> admins = adminUserService.getAdminList(page, size, sort);
        return ResponseEntity.ok(admins);
    }

    // 관리자 차단/차단해제
    @PatchMapping("/admins/{adminId}/block")
    public ResponseEntity<Void> blockAdmin(@PathVariable Integer adminId, @RequestBody UserBlockRequestDto requestDto) {
        adminUserService.blockAdmin(adminId, requestDto);
        return ResponseEntity.ok().build();
    }

    // 관리자 생성
    @PostMapping("/admins")
    public ResponseEntity<Void> createAdmin(@RequestBody AdminCreateRequestDto requestDto) {
        adminUserService.createAdmin(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
