package com.cryptory.be.news.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsServiceImpl implements NewsService {

    // TODO 뉴스 api 이용한 조회(뉴스를 저장할 필요는 없음)

}
