//package com.cryptory.be.user.controller;
//
//import com.cryptory.be.user.dto.AdminAuthDto;
//import com.cryptory.be.user.service.AdminService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
////@RequestMapping("/admin")
//@RequiredArgsConstructor
//public class AdminController {
//
//    private final AdminService adminService;
//
//    @PostMapping("/admin/signup")
//    public ResponseEntity<?> adminSignup(@RequestBody AdminAuthDto signupDto) {
//        adminService.adminSignup(signupDto);
//        return ResponseEntity.ok("관리자 계정 생성 완료");
//    }
//
//    @GetMapping("/api/v1/admin/hello")
//    public ResponseEntity<?> hello() {
//        return ResponseEntity.ok("hello");
//    }
//
//}
