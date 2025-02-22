package com.cryptory.be.admin.controller;

import com.cryptory.be.admin.dto.issue.*;
import com.cryptory.be.admin.service.AdminIssueService;
import com.cryptory.be.admin.service.AdminPostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.cryptory.be.admin.controller
 * fileName       : AdminIssueController
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminIssueController {
    private final AdminIssueService adminIssueService;
    private final AdminPostCommentService adminPostCommentService;

    // 특정 코인의 이슈 목록 조회
    @GetMapping("/coins/{coinId}/issues")
    public ResponseEntity<Page<IssueListResponseDto>> getIssueList(
            @PathVariable Integer coinId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort) {

        Page<IssueListResponseDto> issues = adminIssueService.getIssueList(coinId, page, size, sort);
        return ResponseEntity.ok(issues);
    }

    // 특정 코인의 이슈 생성
    @PostMapping("/coins/{coinId}/issues")
    public ResponseEntity<Void> createIssue(@PathVariable Integer coinId, @RequestBody IssueCreateRequestDto requestDto) {
        Integer newIssueId = adminIssueService.createIssue(coinId, requestDto);
        return ResponseEntity.created(URI.create("/api/v1/admin/issues/" + newIssueId)).build(); // 201 Created, Location 헤더
    }

    // 이슈 상세 조회
    @GetMapping("/issues/{issueId}")
    public ResponseEntity<IssueDetailResponseDto> getIssueDetails(@PathVariable Integer issueId) {
        IssueDetailResponseDto issue = adminIssueService.getIssueDetails(issueId);
        return ResponseEntity.ok(issue);
    }

    // 이슈 수정
    @PutMapping("/issues/{issueId}")
    public ResponseEntity<Void> updateIssue(@PathVariable Integer issueId, @RequestBody IssueUpdateRequestDto requestDto) {
        adminIssueService.updateIssue(issueId, requestDto);
        return ResponseEntity.ok().build();
    }

    // 이슈 삭제 (체크박스 활용한 일괄삭제 가능, 논리적 삭제임)
    @PatchMapping("/issues")
    public ResponseEntity<Void> deleteIssues(@RequestParam("ids") List<Integer> ids, @RequestBody Map<String, Boolean> body){
        if (body.containsKey("isDeleted") && body.get("isDeleted")) {
            adminIssueService.deleteIssues(ids);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // 토론방 댓글 목록 조회
    @GetMapping("/issues/{issueId}/comments")
    public ResponseEntity<Page<IssueCommentListResponseDto>> getIssueComments(
            @PathVariable Integer issueId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort) {

        Page<IssueCommentListResponseDto> comments = adminIssueService.getIssueComments(issueId, page, size, sort);
        return ResponseEntity.ok(comments);
    }

    // 토론방 댓글 삭제 (논리적 삭제)
    @PatchMapping("/issue-commmets/{commentId}")
    public ResponseEntity<Void> deleteIssueComment(@PathVariable Integer commentId, @RequestBody Map<String, Boolean> body) {
        if (body.containsKey("isDeleted") && body.get("idDeleted")) {
            adminIssueService.deleteIssueComment(commentId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // 게시글 강제 삭제 (일괄, 논리적 삭제)
    @PatchMapping("/posts")
    public ResponseEntity<Void> deletePosts(@RequestParam("ids") List<Integer>, @RequestBody Map<String, Boolean> body) {
        if(body.containsKey("isDeleted") && body.get("idDeleted")) {
            adminPostCommentService.deletePosts(ids);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // 게시물 댓글 강제 삭제 (논리적 삭제)
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId, @RequestBody Map<String, Boolean> body){
        if (body.containsKey("isDeleted") && body.get("isDeleted")) {
            adminPostCommentService.deleteComment(commentId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
