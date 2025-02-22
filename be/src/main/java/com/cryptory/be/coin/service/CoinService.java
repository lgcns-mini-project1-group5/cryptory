package com.cryptory.be.coin.service;

import java.util.List;

import com.cryptory.be.coin.dto.CoinDetailDto;
import org.springframework.http.ResponseEntity;

import com.cryptory.be.coin.dto.CoinDto;

public interface CoinService {
	List<CoinDto> getCoins();
	CoinDetailDto getCoinDetail(long coinId);
	ResponseEntity<String> getCoinNews(long coinId);
}
