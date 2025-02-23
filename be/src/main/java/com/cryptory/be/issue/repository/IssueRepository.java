package com.cryptory.be.issue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cryptory.be.chart.domain.Chart;
import com.cryptory.be.issue.domain.Issue;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
	Issue findByChartId(Long chartId);
	List<Issue> findAllByCoinId(Long coinId);
}
