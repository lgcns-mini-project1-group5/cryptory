package com.cryptory.be.news.controller;

import com.cryptory.be.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/news")
public class NewsController {

    private final NewsService newsService;

    // TODO 뉴스 api 이용한 조회(뉴스를 저장할 필요는 없음) - 클라이언트에서 호출

}
