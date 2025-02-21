package com.cryptory.be.coin.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.cryptory.be.coin.domain.Coin;
import com.cryptory.be.coin.dto.CoinDto;
import com.cryptory.be.coin.dto.CoinListResponse;
import com.cryptory.be.coin.repository.CoinRepository;

@Service
public class CoinServiceImpl implements CoinService {
	
	@Autowired
	private CoinRepository coinRepository;

	// 코인 목록 조회
	@Override
	public List<CoinListResponse> selectCoinList() {
		
		List<CoinListResponse> results = new ArrayList<>();
		List<Coin> coinEntityList = coinRepository.findAll();
		coinEntityList.forEach(dto -> results.add(new ModelMapper().map(dto, CoinListResponse.class)));
		
		return results;
	}

	// 특정 코인 상세 조회
	@Override
	public CoinDto selectCoinDetail(long coinId) {
		return null;
	}
	
	// 특정 코인 뉴스 조회
	@Override
	public ResponseEntity<String> getCoinNews(long coinId) {
		
		
		
		String query = "이더리움";
        //String encode = Base64.getEncoder().encodeToString(query.getBytes(StandardCharsets.UTF_8));
    
        URI uri = UriComponentsBuilder.fromUriString("https://openapi.naver.com/")
        .path("v1/search/news.json")
        .queryParam("query", query)
        .queryParam("display", 10)
        .queryParam("start", 1)
        .queryParam("sort", "sim")
        .encode()
        .build()
        .toUri();

        RequestEntity<Void> req = RequestEntity
        .get(uri)
        .header("X-Naver-Client-Id", "BCUvUSfJJO9MFsHgM4K0")
        .header("X-Naver-Client-Secret", "OJ8wojDO42")
        .build();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
        
        return resp;
        
	}

}
