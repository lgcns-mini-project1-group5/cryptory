package com.cryptory.be.admin.controller;

import com.cryptory.be.admin.dto.dashboard.DashboardStatsResponseDto;
import com.cryptory.be.admin.service.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.cryptory.be.admin.controller
 * fileName       : AdminDashboardController
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */
@RestController
@RequestMapping("/api/v1/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {
    private final AdminDashboardService adminDashboardService;

    // 대시보드 통계 조회
    @GetMapping
    public ResponseEntity<DashboardStatsResponseDto> getDashboardStats(
            @RequestParam(defaultValue = "day") String period,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        DashboardStatsResponseDto stats = adminDashboardService.getDashboardStats(period, startDate, endDate);
        return ResponseEntity.ok(stats);
    }
}
