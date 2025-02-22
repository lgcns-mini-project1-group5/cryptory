package com.cryptory.be.coin.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cryptory.be.chart.domain.Chart;
import com.cryptory.be.chart.repository.ChartRepository;
import com.cryptory.be.chart.service.ChartService;
import com.cryptory.be.coin.domain.Coin;
import com.cryptory.be.coin.dto.CoinDetailDto;
import com.cryptory.be.issue.service.IssueService;
import com.cryptory.be.news.service.NewsService;
import com.cryptory.be.openapi.client.UpbitClient;
import com.cryptory.be.openapi.dto.Candle;
import com.cryptory.be.openapi.dto.Market;
import com.cryptory.be.openapi.service.UpbitService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import com.cryptory.be.coin.dto.CoinDto;
import com.cryptory.be.coin.repository.CoinRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoinServiceImpl implements CoinService {

    private final CoinRepository coinRepository;
    private final ModelMapper modelMapper;

    private final ChartRepository chartRepository;

    private final UpbitService upbitService;

    // 애플리케이션 시작 시 자동 db 저장
    @PostConstruct
    public void fetchInitialData() {
        // 업비트에서 KRW로 거래되는 코인 목록 조회
        List<Market> coins = upbitService.getCoinsFromUpbit();

        // 코인 목록 저장
        coinRepository.saveAll(coins.stream()
                .map(coin -> Coin.builder()
                        .koreanName(coin.getKoreanName())
                        .englishName(coin.getEnglishName())
                        .code(coin.getMarket())
                        .build())
                .toList());

        /*
         * 차트 데이터 가져오는 작업(원래는 저장되는 코인에 대한 차트를 모두 저장해야 함)
         * 하지만 batch 사용 안하고, MVP 개발이므로 인기 코인 임의 5개 선정
         * 원래는 모든 코인에 대한 차트 데이터 가져와야 함
         * 모든 코인에 대한 차트 데이터 가져와서 저장하는 건 지금 필요 없고, 양이 너무 많음
         */
        String[] popularCoins = {"KRW-BTC", "KRW-ETH", "KRW-DOGE", "KRW-XRP", "KRW-ADA"};
//		upbitService.getCharts("KRW-BTC").forEach(chart -> {
//			log.info("chart: {}", chart);
//		});

        for (String coin : popularCoins) {
            List<Candle> candles = upbitService.getCharts(coin);

            chartRepository.saveAll(candles.stream()
                    .map(candle -> Chart.builder()
                            .date(candle.getCandleDateTime())
                            .openingPrice(candle.getOpeningPrice())
                            .highPrice(candle.getHighPrice())
                            .lowPrice(candle.getLowPrice())
                            .tradePrice(candle.getTradePrice())
                            .changeRate(candle.getChangeRate())
                            .changePrice(candle.getChangePrice())
                            .coin(coinRepository.findByCode(candle.getMarket()))
                            .build())
                    .toList());
        }

        // TODO 이미지, 색깔 등록 필요
    }

    // 코인 목록 조회
    @Override
    public List<CoinDto> getCoins() {
        return coinRepository.findAll().stream()
                .filter(Coin::isDisplayed)
                .map(coin -> modelMapper.map(coin, CoinDto.class))
                .toList();
    }

    // 특정 코인 상세 조회
    @Override
    public CoinDetailDto getCoinDetail(long coinId) {
        return null;
    }

    // 특정 코인 뉴스 조회
    @Override
    public ResponseEntity<String> getCoinNews(long coinId) {

        String query = "이더리움";
        //String encode = Base64.getEncoder().encodeToString(query.getBytes(StandardCharsets.UTF_8));

        URI uri = UriComponentsBuilder.fromUriString("https://openapi.naver.com/")
                .path("v1/search/news.json")
                .queryParam("query", query)
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "sim")
                .encode()
                .build()
                .toUri();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", "BCUvUSfJJO9MFsHgM4K0")
                .header("X-Naver-Client-Secret", "OJ8wojDO42")
                .build();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

        return resp;

    }

}
