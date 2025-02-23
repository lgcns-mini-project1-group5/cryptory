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
import java.util.NoSuchElementException;

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

    // 사용자 목록 조회
    @GetMapping
    public ResponseEntity<?> getUserList(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort) {
        try {
            Page<UserListResponseDto> users = adminUserService.getUserList(keyword, page, size, sort);
            return ResponseEntity.ok(users);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에서 오류가 발생했습니다.");
        }

    }

    // 사용자 차단/차단 해제
    @PatchMapping("/{userId}/block")
    public ResponseEntity<?> blockUser(@PathVariable Integer userId, @RequestBody UserBlockRequestDto requestDto) {
        try{
            adminUserService.blockUser(userId, requestDto);
            return ResponseEntity.ok().build();
        }catch (NoSuchElementException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id: " + userId + "가 존재하지 않습니다.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에서 오류가 발생했습니다.");
        }

    }


    // 관리자 목록 조회
    @GetMapping("/admins")
    public ResponseEntity<?> getAdminList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort) {
        try{
            Page<AdminListResponseDto> admins = adminUserService.getAdminList(page, size, sort);
            return ResponseEntity.ok(admins);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에서 오류가 발생했습니다.");
        }

    }

    // 관리자 차단/차단 해제
    @PatchMapping("/admins/{userId}/block")
    public ResponseEntity<?> blockAdmin(@PathVariable Integer userId, @RequestBody UserBlockRequestDto requestDto){
        try{
            adminUserService.blockAdmin(userId, requestDto);
            return ResponseEntity.ok().build();
        }catch (NoSuchElementException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id: " + userId + "가 존재하지 않습니다.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에서 오류가 발생했습니다.");
        }
    }

    // 관리자 생성
    @PostMapping("/admins")
    public ResponseEntity<?> createAdmin(@RequestBody AdminCreateRequestDto requestDto) {
        try{
            adminUserService.createAdmin(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에서 오류가 발생했습니다.");
        }
    }
}
