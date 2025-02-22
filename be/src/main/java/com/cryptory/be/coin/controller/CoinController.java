package com.cryptory.be.coin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptory.be.coin.dto.CoinDto;
import com.cryptory.be.coin.dto.CoinListResponse;
import com.cryptory.be.coin.service.CoinService;

@RestController
@RequestMapping("/api/v1")
public class CoinController {
	
	@Autowired
	private CoinService coinService;
	
	// 코인 목록 조회
	@GetMapping("/coins")
	public ResponseEntity<Object> openCoinList() throws Exception {
		
		List<CoinListResponse> coinList = coinService.selectCoinList();
		
		try {
			return new ResponseEntity<>(coinList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("코인 목록 조회 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 특정 코인 상세 조회
	@GetMapping("/coins/{coinId}")
	public ResponseEntity<Object> openCoinDetail(@PathVariable("coinId") long coinId) {
		
		CoinDto selectedCoinDetail = coinService.selectCoinDetail(coinId);
		
		return ResponseEntity.status(HttpStatus.OK).body(selectedCoinDetail);
		
	}
	
	// 특정 코인 뉴스 조회 - 네이버 뉴스
	@GetMapping("/coins/{coinId}/news")
	public ResponseEntity<String> searchCoinNews(@PathVariable("coinId") long coinId) {
		return coinService.getCoinNews(coinId);
	}
	
	
	// TODO 특정 코인 이슈 목록 조회
	
	// TODO 특정 코인 이슈 상세 조회 (토론방)
	
	// TODO 특정 코인 이슈에 대한 코멘트 CRUD
}
