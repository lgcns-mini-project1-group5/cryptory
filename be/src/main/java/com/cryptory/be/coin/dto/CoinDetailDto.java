package com.cryptory.be.coin.dto;

import com.cryptory.be.coin.domain.CoinSymbol;
import lombok.Data;

@Data
public class CoinDetailDto {
    private Long coinId;
    private String name;
    private String code;

    private CoinSymbol coinSymbol; // 심볼 정보

    private double tradePrice; // 현재가
    private double changeRate; // 변화율
    private double changePrice; // 변화 금액
    private long timestamp; // 최근 거래 시간

    // 차트 리스트(차트 넘버, 날짜, 시가, 종가, 고가, 정가, 변화율)
//    private List<ChartDto> chartList;

    // 이슈 리스트(이슈 넘버, 차트 넘버, 날짜)
//    private List<IssueDto> issueList;
}
