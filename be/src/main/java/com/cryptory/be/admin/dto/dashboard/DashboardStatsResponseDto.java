package com.cryptory.be.admin.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Jacksonized
public class DashboardStatsResponseDto {
    private Long totalVisitors; // 총 방문자 수 + 비회원
    private Long totalPageViews;
    private Long aiApiCalls;
    private List<CoinTrafficDto> trafficByCoin;
    public DashboardStatsResponseDto() {
        this.totalVisitors = 0L;
        this.totalPageViews = 0L;
        this.aiApiCalls = 0L;
        this.trafficByCoin = null; // 또는 빈 리스트
    }
}
