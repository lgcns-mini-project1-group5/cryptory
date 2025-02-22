package com.cryptory.be.chart.dto;

import lombok.Data;

@Data
public class ChartDto {
    private String date; // 날짜
    private double openingPrice; // 시가
    private double highPrice; // 종가
    private double lowPrice; // 고가
    private double tradePrice; // 저가
    private double changeRate; // 변화율
}
