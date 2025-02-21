package com.cryptory.be.coin.dto;

import com.cryptory.be.coin.domain.CoinSymbol;

import lombok.Data;

@Data
public class CoinListResponse {
	private Long crypto_id;
    private String name;
    private Long crypto_color;
    
    // 심볼 정보
    private CoinSymbol coinSymbol;
    
    // 차트 리스트
    
}
