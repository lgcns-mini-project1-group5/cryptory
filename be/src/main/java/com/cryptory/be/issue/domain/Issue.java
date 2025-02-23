package com.cryptory.be.issue.domain;

import com.cryptory.be.chart.domain.Chart;
import com.cryptory.be.global.entity.BaseTimeEntity;
import com.cryptory.be.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    private String title;

    private String content;

    // 하나의 차트에 대해 이슈 한 개만 등록
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chart_id")
    private Chart chart;

    @Builder
    public Issue(String title, String content, Chart chart) {
        this.title = title;
        this.content = content;
        this.chart = chart;
    }
}
