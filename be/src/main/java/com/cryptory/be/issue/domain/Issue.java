package com.cryptory.be.issue.domain;

import com.cryptory.be.chart.domain.Chart;
import com.cryptory.be.coin.domain.Coin;
import com.cryptory.be.global.entity.BaseTimeEntity;
import com.cryptory.be.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "issues")
public class Issue extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이슈 제목
    private String title;

    // 이슈 요약 내용
    private String content;
    
    // 뉴스 제목
    
    // 뉴스 출처
    
    // 이슈 생성 타입
    
    // AI 생성 요청 횟수
    
    // 회원ID
    
    // 삭제 여부

    // 하나의 차트에 대해 이슈 한 개만 등록
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chart_id")
    private Chart chart;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coin_id")
    private Coin coin;
}
