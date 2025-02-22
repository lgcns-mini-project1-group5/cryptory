package com.cryptory.be.coin.dto;

import com.cryptory.be.coin.domain.CoinSymbol;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CoinDto {
	private Long crypto_id;
    private String name;
    private Long crypto_color;
    
    // 심볼 정보
    private CoinSymbol coinSymbol;
    
    // 차트 리스트
    
    // 게시글 리스트
}
