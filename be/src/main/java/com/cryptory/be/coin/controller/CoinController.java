package com.cryptory.be.coin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cryptory.be.coin.dto.CoinDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptory.be.coin.dto.CoinDto;
import com.cryptory.be.coin.service.CoinService;

@RestController
@RequestMapping("/api/v1/coins")
@RequiredArgsConstructor
public class CoinController {

	private final CoinService coinService;
	
	// 코인 목록 조회
	@GetMapping
	public ResponseEntity<Object> openCoinList() throws Exception {
		
		List<CoinDto> coinList = coinService.getCoins();
		
		try {
			return ResponseEntity.ok(coinList);
		} catch (Exception e) {
			return new ResponseEntity<>("코인 목록 조회 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 특정 코인 상세 조회
	@GetMapping("/{coinId}")
	public ResponseEntity<CoinDetailDto> openCoinDetail(@PathVariable("coinId") long coinId) {
		
		CoinDetailDto selectedCoinDetail = coinService.getCoinDetail(coinId);
		
		return ResponseEntity.ok(selectedCoinDetail);
		
	}
	
	// 특정 코인 뉴스 조회 - 네이버 뉴스
	@GetMapping("/{coinId}/news")
	public ResponseEntity<String> searchCoinNews(@PathVariable("coinId") long coinId) {
		return coinService.getCoinNews(coinId);
	}

}
