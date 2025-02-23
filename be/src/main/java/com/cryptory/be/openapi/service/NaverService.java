package com.cryptory.be.openapi.service;

import com.cryptory.be.openapi.client.NaverClient;
import com.cryptory.be.openapi.dto.NaverNews;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverService {

    private final NaverClient naverClient;

    public List<NaverNews> getNaverNewsWithWord(String coinName) {
        return naverClient.getNaverNewsWithWord(coinName);
    }

}
