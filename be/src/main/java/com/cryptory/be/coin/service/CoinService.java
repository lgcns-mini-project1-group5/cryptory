package com.cryptory.be.coin.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cryptory.be.coin.dto.CoinDto;
import com.cryptory.be.coin.dto.CoinListResponse;

public interface CoinService {
	List<CoinListResponse> selectCoinList();
	CoinDto selectCoinDetail(long coinId);
	public ResponseEntity<String> getCoinNews(long coinId);
}
