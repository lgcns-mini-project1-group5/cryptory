package com.cryptory.be.issue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryptory.be.issue.domain.IssueComment;

public interface IssueCommentRepository extends JpaRepository<IssueComment, Long> {
	List<IssueComment> findByIssueId(Long issueId);
}
