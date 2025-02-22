package com.cryptory.be.chart.domain;

import com.cryptory.be.coin.domain.Coin;
import com.cryptory.be.global.entity.BaseTimeEntity;
import com.cryptory.be.issue.domain.Issue;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "charts")
public class Chart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 날짜
    private String date;

    // 시가
    private Double openingPrice;

    // 고가
    private Double highPrice;

    // 저가
    private Double lowPrice;

    // 종가
    private Double tradePrice;

    // 누적 거래량
    private Double tradeVolume;

    // 전일 종가 대비 변화량
    private Double changeRate;

    // 전일 종가 대비 변화 금액
    private Double changePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coin_id")
    private Coin coin;

    // TODO 차트와 이슈 관계 추가
    // 차트 하나에 이슈 한개
    @OneToOne(mappedBy = "chart")
    private Issue issue;

}
