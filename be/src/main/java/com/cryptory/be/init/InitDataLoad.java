package com.cryptory.be.init;

import com.cryptory.be.chart.domain.Chart;
import com.cryptory.be.chart.repository.ChartRepository;
import com.cryptory.be.coin.domain.Coin;
import com.cryptory.be.coin.domain.CoinSymbol;
import com.cryptory.be.coin.domain.CoinSymbolEnum;
import com.cryptory.be.coin.repository.CoinRepository;
import com.cryptory.be.openapi.dto.Candle;
import com.cryptory.be.openapi.dto.Market;
import com.cryptory.be.openapi.service.UpbitService;
import com.cryptory.be.user.domain.User;
import com.cryptory.be.user.repository.UserRepository;
import com.cryptory.be.user.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitDataLoad {

    private final UpbitService upbitService;

    private final UserRepository userRepository;
    private final CoinRepository coinRepository;
    private final ChartRepository chartRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    // 애플리케이션 시작 시 자동 db 저장
    @PostConstruct
    public void fetchInitialData() {

        Set<String> hiddenKeys = Set.of(
                "MTL", "XRP", "GRS", "IOST", "HI", "ONG", "CB", "ELF", "QTUM",
                "BTT", "MOC", "ARGO", "TT", "GMB", "MBL", "MLK", "STPT", "STMX", "DKA",
                "AHT", "BORA", "JST", "CRO", "TON", "SOLA", "HUNT", "DOT", "STRAT",
                "AQT", "GLM", "META", "FCT", "KOB", "SAND", "HPO", "STRK", "NPXS",
                "STX", "MATIC", "T", "GMT", "EGLD", "GRT", "BLUR"
        );

        // 업비트에서 KRW로 거래되는 코인 목록 조회
        List<Market> coinsByUpbit = upbitService.getCoinsFromUpbit();

        // 코인 목록 저장
        List<Coin> coins = coinsByUpbit.stream()
                .map(coin -> {
                    CoinSymbolEnum coinSymbolEnum = CoinSymbolEnum.fromMarket(coin.getMarket());
                    CoinSymbol coinSymbol = coinSymbolEnum.toCoinSymbol();
                    boolean isDisplayed = !hiddenKeys.contains(coin.getMarket().substring(4)); // 숨길 코인 확인

                    return Coin.builder()
                            .koreanName(coin.getKoreanName())
                            .englishName(coin.getEnglishName())
                            .code(coin.getMarket())
                            .coinSymbol(coinSymbol)
                            .isDisplayed(isDisplayed)
                            .build();
                })
                .toList();

        coinRepository.saveAll(coins);

        /*
         * 차트 데이터 가져오는 작업(원래는 저장되는 코인에 대한 차트를 모두 저장해야 함)
         * 하지만 batch 사용 안하고, MVP 개발이므로 인기 코인 임의 5개 선정
         * 원래는 모든 코인에 대한 차트 데이터 가져와야 함
         * 모든 코인에 대한 차트 데이터 가져와서 저장하는 건 지금 필요 없고, 양이 너무 많음
         */
        String[] popularCoins = {"KRW-BTC", "KRW-ETH", "KRW-DOGE", "KRW-XRP", "KRW-ADA"};

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

        // 관리자 초기 데이터 저장
        User admin = User.createAdminUser("admin", passwordEncoder.encode("1234"), "관리자");
        userRepository.save(admin);

    }
}
