package com.cryptory.be.admin.service;

import com.cryptory.be.admin.dto.dashboard.DashboardStatsResponseDto;

/**
 * packageName    : com.cryptory.be.admin.service
 * fileName       : AdminDashboardService
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */
public interface AdminDashboardService {
    DashboardStatsResponseDto getDashboardStats(String period, String startDate, String endDate);
}
