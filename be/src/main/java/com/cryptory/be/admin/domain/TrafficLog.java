package com.cryptory.be.admin.domain;

import com.cryptory.be.coin.domain.Coin;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * packageName    : com.cryptory.be.admin.domain
 * fileName       : TrafficLog
 * author         : 조영상
 * date           : 2/24/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/24/25         조영상        최초 생성
 */
/*
요구사항:

    대시보드 통계 조회:
        총 방문자 수 (회원 + 비회원)
        총 페이지 뷰 수
        AI API 호출 횟수
        코인별 트래픽 (메인 페이지에 노출된 코인만)
        기간별 필터링: 일별, 주별, 월별, 전체
*/

@Entity
@Getter
@NoArgsConstructor
@Table(name = "traffic_logs")
@EntityListeners(AuditingEntityListener.class)
public class TrafficLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coin_id")
    private Coin coin;

    private LocalDate date; // 통계 기준 날짜

    private Long pageViews;

    private Long users; // 회원 + 비회원

    private Long apiCalls;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public TrafficLog(Coin coin, LocalDate date, Long pageViews, Long users, Long apiCalls) {
        this.coin = coin;
        this.date = date;
        this.pageViews = pageViews;
        this.users = users;
        this.apiCalls = apiCalls;
    }

    public void incrementPageViews() {
        this.pageViews++;
    }

    public void incrementApiCalls() {
        this.apiCalls++;
    }
}
