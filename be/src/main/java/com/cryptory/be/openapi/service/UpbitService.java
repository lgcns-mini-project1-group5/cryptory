package com.cryptory.be.openapi.service;

import com.cryptory.be.openapi.client.UpbitClient;
import com.cryptory.be.openapi.dto.Candle;
import com.cryptory.be.openapi.dto.Market;
import com.cryptory.be.openapi.dto.Ticker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpbitService {

    private final UpbitClient upbitClient;

    // 코인이 너무 많아서 KRW로 거래되는 코인들만 조회
    public List<Market> getCoinsFromUpbit() {
        try {
            return upbitClient.getCoins().stream()
                    .filter(market -> market.getMarket().startsWith("KRW"))
                    .toList();
        } catch (Exception e) {
            log.error("error: {}", e.getMessage() + "\n업비트 코인 조회 실패");
            return List.of();
        }
    }

    // 코인 차트 가져오기
    public List<Candle> getCharts(String market) {
        return upbitClient.getCharts(market);
    }

    // 코인들 현재가 가져오기
    public List<Ticker> getTickers(String ... market) {
        return upbitClient.getTickers(market);
    }

}
