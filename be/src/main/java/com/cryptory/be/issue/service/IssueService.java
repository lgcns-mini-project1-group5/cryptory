package com.cryptory.be.issue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssueService {
    // TODO 이슈 목록 조회(코인 상세 페이지 들어갈 때 필요) - api는 필요 없음


}
