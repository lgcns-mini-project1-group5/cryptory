package com.cryptory.be.coin.service;

import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cryptory.be.chart.repository.ChartRepository;
import com.cryptory.be.coin.domain.Coin;
import com.cryptory.be.coin.dto.CoinDetailDto;
import com.cryptory.be.coin.dto.CoinNewsDto;
import com.cryptory.be.global.util.DateFormat;
import com.cryptory.be.openapi.dto.NaverNews;
import com.cryptory.be.openapi.service.NaverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import com.cryptory.be.coin.dto.CoinDto;
import com.cryptory.be.coin.repository.CoinRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoinServiceImpl implements CoinService {

    private final NaverService naverService;

    private final CoinRepository coinRepository;
    private final ChartRepository chartRepository;


    // 코인 목록 조회
    @Override
    public List<CoinDto> getCoins() {
        return coinRepository.findAll().stream()
                .filter(Coin::isDisplayed)
                .map(coin -> CoinDto.builder()
                        .coinId(coin.getId())
                        .koreanName(coin.getKoreanName())
                        .englishName(coin.getEnglishName())
                        .code(coin.getCode())
                        .coinSymbol(coin.getCoinSymbol())
                        // TODO 현재가 api 호출하여 추가
//                        .tradePrice(coin.getTradePrice())
//                        .signedChangePrice(coin.getSignedChangePrice())
//                        .signedChangeRate(coin.getSignedChangeRate())
                        .build())
                .toList();
    }

    // 특정 코인 상세 조회
    @Override
    public CoinDetailDto getCoinDetail(Long coinId) {
        return null;
    }

    // 특정 코인 뉴스 조회
    @Override
    public List<CoinNewsDto> getCoinNews(Long coinId) {
        Coin coin = coinRepository.findById(coinId)
                .orElseThrow(() -> new IllegalArgumentException("해당 코인이 없습니다."));

        List<NaverNews> naverNewsList = naverService.getNaverNews(coin.getKoreanName());

        return naverNewsList.stream()
                .map(naverNews -> {
                    try {
                        return new CoinNewsDto(naverNews.getTitle(), naverNews.getLink(),
                                naverNews.getDescription(), DateFormat.formatNewsDate(naverNews.getPubDate()));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

}
