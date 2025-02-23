package com.cryptory.be.admin.service;

import com.cryptory.be.admin.dto.issue.*;
import com.cryptory.be.chart.domain.Chart;
import com.cryptory.be.chart.repository.ChartRepository;
import com.cryptory.be.coin.domain.Coin;
import com.cryptory.be.coin.repository.CoinRepository;
import com.cryptory.be.issue.domain.Issue;
import com.cryptory.be.issue.domain.IssueComment;
import com.cryptory.be.issue.repository.IssueCommentRepository;
import com.cryptory.be.issue.repository.IssueRepository;
import com.cryptory.be.user.domain.User;
import com.cryptory.be.user.dto.PrincipalUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final IssueRepository issueRepository;
    private final IssueCommentRepository issueCommentRepository;
    private final ChartRepository chartRepository;

    @Override
    public Page<IssueListResponseDto> getIssueList(Long coinId, int page, int size, String sort) {
        Sort sorting = parseSort(sort);
        Pageable pageable = PageRequest.of(page, size, sorting);

        // 특정 코인에 대한 이슈만 조회, 삭제되지 않은 ISSUE만 조회
        Page<Issue> issues = issueRepository.findByCoinIdAndIsDeletedFalse(coinId, pageable);
        return issues.map(this::convertToIssueListResponseDto);
    }

    @Transactional
    @Override
    public Long createIssue(Long coinId, IssueCreateRequestDto requestDto) {
        Coin coin = coinRepository.findById(coinId)
                .orElseThrow(() -> new NoSuchElementException("해당 코인을 찾을 수 없습니다. ID: " + coinId));

        // date와 coinId로 chart 조회
        Chart chart = chartRepository.findByDateAndCoinId(requestDto.getDate().toString(), coinId.longValue())
                .orElseThrow(() -> new NoSuchElementException("해당 날짜와 코인에 대한 차트를 찾을 수 없습니다. Date: " + requestDto.getDate() + ", Coin ID: " + coinId));

        // 일반 로그인으로 로그인한 관리자 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUserDetails principalDetails = (PrincipalUserDetails) authentication.getPrincipal(); // PrincipalUserDetails로 캐스팅
        User adminUser = principalDetails.getUser();

        Issue newIssue = Issue.builder()
                .date(requestDto.getDate())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .newsTitle(requestDto.getNewsTitle())
                .source(requestDto.getSource())
                .type("MANUAL") // 관리자가 생성 이슈니까
                .user(adminUser) // 작성자 설정 (관리자)
                .coin(coin)      // coin 설정
                .chart(chart) // chart 설정
                .requestCount(0L)
                .isDeleted(false)
                .build();

        // issueRepository 생성 필요, 현재 기준 없음
        return issueRepository.save(newIssue).getId();
    }

    @Override
    public IssueDetailResponseDto getIssueDetails(Long issueId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new NoSuchElementException("해당 이슈를 찾을 수 없습니다. ID: " + issueId));

        return convertToIssueDetailResponseDto(issue);
    }

    @Transactional
    @Override
    public void updateIssue(Long issueId, IssueUpdateRequestDto requestDto) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new NoSuchElementException("해당 이슈를 찾을 수 없습니다. ID: " + issueId));
        try {
            issue.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getNewsTitle(), requestDto.getSource());
        } catch (IllegalArgumentException e) {
            // 유효성 검사 실패
            throw e;
        }
    }

    @Transactional
    @Override
    public void deleteIssues(List<Long> ids) {
        List<Issue> issues = issueRepository.findAllById(ids);
        issues.forEach(Issue::delete);
        //issueRepository.softDeleteByIds(ids);
    }

    @Override
    public Page<IssueCommentListResponseDto> getIssueComments(Long issueId, int page, int size, String sort) {
        Sort sorting = parseSort(sort);
        Pageable pageable = PageRequest.of(page, size, sorting);
        Page<IssueComment> comments = issueCommentRepository.findCommentsByIssueId(issueId, pageable);
        return comments.map(this::convertToIssueCommentListResponseDto);
    }

    @Transactional
    @Override
    public void deleteIssueComment(Long commentId) {
        IssueComment comment = issueCommentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("해당 토론방 댓글을 찾을 수 없습니다. ID: " + commentId));
        comment.delete();
    }

    // Issue -> IssueListResponseDto 변환
    private IssueListResponseDto convertToIssueListResponseDto(Issue issue) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUserDetails principalDetails = (PrincipalUserDetails) authentication.getPrincipal();
        User adminUser = principalDetails.getUser();

        return IssueListResponseDto.builder()
                .issueId(issue.getId())
                .date(issue.getDate())
                .title(issue.getTitle())
                .createdBy(adminUser.getId())
                .createdAt(issue.getCreatedAt())
                .updatedAt(issue.getUpdatedAt())
                .build();
    }

    // Issue -> IssueDetailResponseDto 변환
    private IssueDetailResponseDto convertToIssueDetailResponseDto(Issue issue) {
        return IssueDetailResponseDto.builder()
                .issueId(issue.getId())
                .date(issue.getDate())
                .title(issue.getTitle())
                .content(issue.getContent())
                .createdBy(issue.getUser().getNickname())
                .createdAt(issue.getCreatedAt())
                .updatedAt(issue.getUpdatedAt())
                .newsTitle(issue.getNewsTitle())
                .source(issue.getSource())
                .type(issue.getType())
                .isDeleted(issue.isDeleted())
                .build();
    }

    // IssueComment -> IssueCommentListResponseDto 변환
    private IssueCommentListResponseDto convertToIssueCommentListResponseDto(IssueComment comment) {
        return IssueCommentListResponseDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .isDeleted(comment.isDeleted())
                .userId(comment.getUser().getId())
                .build();
    }


    private Sort parseSort(String sort) {
        if (sort == null || sort.isEmpty()) {
            return Sort.by("createdAt").descending(); // 기본 정렬 (생성일 내림차순)
        }

        String[] parts = sort.split(",");
        String property = parts[0];
        Sort.Direction direction = Sort.Direction.ASC;
        if (parts.length > 1 && "desc".equalsIgnoreCase(parts[1])) {
            direction = Sort.Direction.DESC;
        }

        return Sort.by(direction, property);
    }
}
