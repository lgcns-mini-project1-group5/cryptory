package com.cryptory.be.admin.service;

import com.cryptory.be.admin.dto.issue.*;
import com.cryptory.be.chart.domain.Chart;
import com.cryptory.be.chart.repository.ChartRepository;
import com.cryptory.be.coin.domain.Coin;
import com.cryptory.be.coin.repository.CoinRepository;
import com.cryptory.be.issue.domain.Issue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * packageName    : com.cryptory.be.admin.service
 * fileName       : AdminIssueServiceImpl
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminIssueServiceImpl implements AdminIssueService {

    private final CoinRepository coinRepository;
    // issue와 issueComment 리포지토리는 추후 생성된다고 가정하고 작성했음
    private final IssueRepository issueRepository;
    private final IssueCommentRepository issueCommentRepository;
    private final ChartRepository chartRepository;

    @Override
    public Page<IssueListResponseDto> getIssueList(Long coinId, int page, int size, String sort) {
        Sort sorting = parseSort(sort);
        Pageable pageable = PageRequest.of(page, size, sorting);

        // 특정 코인에 대한 이슈만 조회, 삭제되지 않은 ISSUE만 조회
        Page<Issue> issues = issueRepository.findByCoin_idAndIsDeletedFalse(coinId, pageable);
        return issues.map(this::convertToIssueListResponseDto);
    }

    @Transactional
    @Override
    public Long createIssue(Long coinId, IssueCreateRequestDto requestDto) {

        Chart chart = chartRepository.findById(requestDto.getChartId())
                .orElseThrow(() -> new NoSuchElementException("해당 차트를 찾을 수 없습니다. ID: " + requestDto.getChartId());

        Issue newIssue = Issue.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .chart(chart)
                .build();

        // issueRepository 생성 필요, 현재 기준 없음
        return issueRepository.save(newIssue).getId();
    }

    @Override
    public IssueDetailResponseDto getIssueDetails(Long issueId) {
        return null;
    }

    @Override
    public void updateIssue(Long issueId, IssueUpdateRequestDto requestDto) {

    }

    @Override
    public void deleteIssues(List<Long> ids) {

    }

    @Override
    public Page<IssueCommentListResponseDto> getIssueComments(Long issueId, int page, int size, String sort) {
        return null;
    }

    @Override
    public void deleteIssueComment(Long commentId) {

    }
}
