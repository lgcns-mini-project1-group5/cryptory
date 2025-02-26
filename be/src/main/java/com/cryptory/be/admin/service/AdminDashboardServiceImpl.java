package com.cryptory.be.admin.service;

import com.cryptory.be.admin.dto.dashboard.AdminDashboardResponseDTO;
import com.cryptory.be.admin.dto.dashboard.Statistics;
import com.cryptory.be.admin.repository.AdminDashboardRepository;
import com.cryptory.be.admin.repository.TrafficLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminDashboardServiceImpl implements AdminDashboardService{
    private final AdminDashboardRepository adminDashboardRepository;
    private final TrafficLogRepository trafficLogRepository;

    @Override
    public AdminDashboardResponseDTO getDashboardStatistics(LocalDate startDate, LocalDate endDate) {
        // TrafficLog 기반 통계
        Statistics visits = trafficLogRepository.getVisitStatistics(startDate, endDate);
        Statistics pageViews = trafficLogRepository.getPageViewStatistics(startDate, endDate);

        // 기존 통계
        Statistics newUsers = adminDashboardRepository.getNewUserStatistics(startDate, endDate);
        Map<String, Statistics> apiCallCounts = adminDashboardRepository.getApiCallStatisticsByCoin(startDate, endDate);

        return AdminDashboardResponseDTO.builder()
                .visits(visits)
                .pageViews(pageViews)
                .newUsers(newUsers)
                .apiCallCounts(apiCallCounts)
                .build();
    }
}
