package com.cryptory.be.issue.service;

import com.cryptory.be.issue.domain.Issue;
import com.cryptory.be.issue.repository.IssueRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cryptory.be.global.util.DateFormat;
import com.cryptory.be.issue.domain.IssueComment;
import com.cryptory.be.issue.dto.CreateIssueCommentDto;
import com.cryptory.be.issue.dto.IssueCommentDto;
import com.cryptory.be.issue.dto.UpdateIssueCommentDto;
import com.cryptory.be.issue.repository.IssueCommentRepository;
import com.cryptory.be.post.domain.Post;
import com.cryptory.be.user.domain.User;
import com.cryptory.be.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssueService {

    private final IssueRepository issueRepository;
    private final IssueCommentRepository issueCommentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    // 특정 이슈 상세 조회 - 토론방 코멘트 전체 조회
    public List<IssueCommentDto> getIssueComments(Long issueId) {

        return issueCommentRepository.findAllByIssueId(issueId).stream()
                .filter(IssueComment::isNotDeleted)
                .map(issueComment -> new IssueCommentDto(
                        issueComment.getId(),
                        issueComment.getContent(),
                        issueComment.getUser().getNickname(),
                        DateFormat.formatDate(issueComment.getCreatedAt())
                ))
                .toList();
    }

    // 이슈 내 코멘트 등록
    @Transactional
    public IssueCommentDto createIssueComment(Long issueId, String userId, CreateIssueCommentDto createIssueCommentDto) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("해당 이슈를 찾을 수 없습니다."));

        IssueComment issueComment = issueCommentRepository.save(IssueComment.builder()
                .content(createIssueCommentDto.getContent())
                .user(user)
                .issue(issue)
                .build());

        return new IssueCommentDto(
                issueComment.getId(),
                issueComment.getContent(),
                issueComment.getUser().getNickname(),
                DateFormat.formatDate(issueComment.getCreatedAt())
        );
    }

    // 이슈 내 코멘트 수정
    @Transactional
    public void updateIssueComment(Long issueId, Long issueCommentId, UpdateIssueCommentDto updateIssueCommentDto) {
        IssueComment issueComment = issueCommentRepository.findById(issueCommentId)
                .orElseThrow(() -> new IllegalArgumentException("코멘트를 찾을 수 없습니다."));

        issueComment.update(updateIssueCommentDto.getContent());
    }

    // 이슈 내 코멘트 삭제
    @Transactional
    public void deleteIssueComment(Long issueId, Long issueCommentId) {
        IssueComment issueComment = issueCommentRepository.findById(issueCommentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 코멘트를 찾을 수 없습니다."));

        issueComment.delete();
    }

}
