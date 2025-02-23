package com.cryptory.be.issue.controller;

import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.cryptory.be.issue.dto.CreateIssueCommentDto;
import com.cryptory.be.issue.dto.IssueCommentDto;
import com.cryptory.be.issue.dto.UpdateIssueCommentDto;
import com.cryptory.be.issue.service.IssueService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coins/{coinId}/issues")
public class IssueController {
	
	private final IssueService issueService;
	
	// 특정 이슈 상세 조회 - 이슈 기본 정보
	
	// 특정 이슈 상세 조회 - AI 관련

    // 특정 이슈 상세 조회 - 토론방 코멘트 전체 조회
	@GetMapping("/{issueId}/comments")
    public ResponseEntity<List<IssueCommentDto>> getIssueComments(@PathVariable("coinId") Long coinId, 
    																@PathVariable("issueId") Long issueId) {
        
		List<IssueCommentDto> issueComments = issueService.getIssueComments(issueId);

        return ResponseEntity.ok(issueComments);
    }

    // 이슈 내 코멘트 등록
	@PostMapping("/{issueId}/comments")
	public ResponseEntity<IssueCommentDto> createIssueComment(Principal principal,
											@PathVariable("coinId") Long coinId,
											@PathVariable("issueId") Long issueId,
								            @RequestPart(value = "issueComment") CreateIssueCommentDto createIssueCommentDto) {
		IssueCommentDto issueComment = issueService.createIssueComment(issueId, principal.getName(), createIssueCommentDto);
		return ResponseEntity.ok(issueComment);
	}

    // 이슈 내 코멘트 수정
	@PatchMapping("/{issueId}/comments/{commentId}")
    public ResponseEntity<?> updateIssueComment(@PathVariable("coinId") Long coinId,
    											@PathVariable("issueId") Long issueId,
                                              @PathVariable("commentId") Long issueCommentId,
                                              @RequestPart(value = "issueComment", required = false) UpdateIssueCommentDto updateIssueCommentDto) {
        issueService.updateIssueComment(issueId, issueCommentId, updateIssueCommentDto);
        return ResponseEntity.ok("코멘트를 업데이트했습니다.");
    }


    // 이슈 내 코멘트 삭제
	@DeleteMapping("/{issueId}/comments/{commentId}")
    public ResponseEntity<?> deletePost(@PathVariable("coinId") Long coinId,
    									@PathVariable("issueId") Long issueId,
                                        @PathVariable("commentId") Long issueCommentId) {
        issueService.deleteIssueComment(issueId, issueCommentId);
        return ResponseEntity.ok("정상적으로 삭제되었습니다.");
    }


}
