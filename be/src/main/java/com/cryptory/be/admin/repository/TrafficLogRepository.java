package com.cryptory.be.admin.repository;

import com.cryptory.be.admin.domain.TrafficLog;
import com.cryptory.be.admin.dto.dashboard.CoinTrafficDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * packageName    : com.cryptory.be.admin.repository
 * fileName       : TrafficLogRepository
 * author         : 조영상
 * date           : 2/24/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/24/25         조영상        최초 생성
 */
public interface TrafficLogRepository extends JpaRepository<TrafficLog, Long> {
    // 일별 통계
    @Query("SELECT SUM(t.pageViews), SUM(t.users), SUM(t.apiCalls) FROM TrafficLog t WHERE t.date = :date")
    List<Object[]> findDailyStats(@Param("date") LocalDate date);

    // 주별 통계 (해당 주차의 시작일~종료일)
    @Query("SELECT SUM(t.pageViews), SUM(t.users), SUM(t.apiCalls) " +
            "FROM TrafficLog t " +
            "WHERE t.date BETWEEN :startDate AND :endDate")
    List<Object[]> findWeeklyStats(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // 월별 통계
    @Query("SELECT SUM(t.pageViews), SUM(t.users), SUM(t.apiCalls) " +
            "FROM TrafficLog t " +
            "WHERE t.date BETWEEN :startDate AND :endDate")
    List<Object[]> findMonthlyStats(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // 전체 통계
    @Query("SELECT SUM(t.pageViews), SUM(t.users), SUM(t.apiCalls) FROM TrafficLog t")
    List<Object[]> findTotalStats();

    // 코인별 트래픽 (특정 기간) - JPQL 수정
    @Query("SELECT new com.example.project.admin.dto.dashboard.CoinTrafficDto(t.coin.id, t.coin.koreanName, t.coin.coinSymbol.symbol, SUM(t.pageViews) + SUM(t.users) + SUM(t.apiCalls)) " +
            "FROM TrafficLog t " +
            "WHERE t.date BETWEEN :startDate AND :endDate AND t.coin.isDisplayed = TRUE " +
            "GROUP BY t.coin.id, t.coin.koreanName, t.coin.coinSymbol.symbol")
    List<CoinTrafficDto> findTrafficByCoin(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
