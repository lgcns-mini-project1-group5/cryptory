package com.cryptory.be.admin.service;

import com.cryptory.be.admin.dto.dashboard.CoinTrafficDto;
import com.cryptory.be.admin.dto.dashboard.DashboardStatsResponseDto;
import com.cryptory.be.admin.repository.TrafficLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminDashboardServiceImpl implements AdminDashboardService{
    private final TrafficLogRepository trafficLogRepository;

    @Override
    public DashboardStatsResponseDto getDashboardStats(String period, String startDate, String endDate) {
        return null;
    }

//    @Override
//    public DashboardStatsResponseDto getDashboardStats(String period, String startDate, String endDate) {
//        LocalDate start = null;
//        LocalDate end = null;
//
//        if(!"total".equalsIgnoreCase(period)) {
//            try{
//                start = LocalDate.parse(startDate);
//                end = LocalDate.parse(endDate);
//            } catch (DateTimeParseException e) {
//                throw new IllegalArgumentException("유효하지 않은 날짜 포맷입니다. YYYY-MM-DD 형식으로 변환해주세요");
//            }
//        }
//        List<Object[]> stats;
//        switch (period.toLowerCase()) {
//            case "day":
//                stats = trafficLogRepository.findDailyStats(start);
//                break;
//            case "week":
//                LocalDate monday = start.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
//                LocalDate sunday = start.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
//                stats = trafficLogRepository.findWeeklyStats(monday, sunday);
//                break;
//            case "month":
//                LocalDate firstDayOfMonth = start.withDayOfMonth(1);
//                LocalDate lastDayOfMonth = end.withDayOfMonth(end.lengthOfMonth());
//                stats = trafficLogRepository.findMonthlyStats(firstDayOfMonth, lastDayOfMonth);
//                break;
//            case "total":
//                stats = trafficLogRepository.findTotalStats();
//                break;
//            default:
//                throw new IllegalArgumentException("Invalid period: " + period);
//        }
//
//        Long totalVisitors = 0L;
//        Long totalPageViews = 0L;
//        Long aiApiCalls = 0L;
//
//        if (stats != null && !stats.isEmpty()) {
//            Object[] result = stats.get(0);
//            totalPageViews = (result[0] != null) ? ((Number) result[0]).longValue() : 0L;
//            totalVisitors = (result[1] != null) ? ((Number) result[1]).longValue() : 0L;
//            aiApiCalls = (result[2] != null) ? ((Number) result[2]).longValue() : 0L;
//        }
//
//        List<CoinTrafficDto> trafficByCoin = trafficLogRepository.findTrafficByCoin(start, end);
//
//        return DashboardStatsResponseDto.builder()
//                .totalVisitors(totalVisitors)
//                .totalPageViews(totalPageViews)
//                .aiApiCalls(aiApiCalls)
//                .trafficByCoin(trafficByCoin)
//                .build();
//    }
}
