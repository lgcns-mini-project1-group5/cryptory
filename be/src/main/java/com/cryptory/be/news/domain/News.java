package com.cryptory.be.news.domain;

import com.cryptory.be.chart.domain.Chart;
import com.cryptory.be.global.entity.BaseTimeEntity;
import com.cryptory.be.issue.domain.Issue;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "news")
public class News extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String link;

    // 하나의 이슈에 대해 뉴스가 여러개
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    private Issue issue;
}
