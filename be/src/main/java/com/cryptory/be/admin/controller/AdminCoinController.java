package com.cryptory.be.admin.controller;

import com.cryptory.be.admin.dto.coin.CoinDetailResponseDto;
import com.cryptory.be.admin.dto.coin.CoinListResponseDto;
import com.cryptory.be.admin.service.AdminCoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : com.cryptory.be.admin.controller
 * fileName       : AdminCoinController
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */
@RestController
@RequestMapping("/api/v1/admin/coins")
@RequiredArgsConstructor
public class AdminCoinController {
    private final AdminCoinService adminCoinService;

    // 코인 목록 조회 (쿼리 파라미터로 검색, 페이징, 정렬)
    @GetMapping
    public ResponseEntity<Page<CoinListResponseDto>> getCoinList(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort){

        Page<CoinListResponseDto> coins = adminCoinService.getCoinList(keyword, page, size, sort);
        return ResponseEntity.ok(coins);
    }

    // 특정 코인 상세 조회
    @GetMapping("/{coinId}")
    public ResponseEntity<CoinDetailResponseDto> getCoinDetails(@PathVariable Integer coinId) {
        CoinDetailResponseDto coin = adminCoinService.getCoinDetails(coinId);
        return ResponseEntity.ok(coin);
    }

    // 코인 메인 페이지 노출 여부 결정
    @PatchMapping("/{coinId}/display")
    public ResponseEntity<Void> updateDisplaySetting(
            @PathVariable Integer coinId,
            @RequestParam("isDisplayed") boolean isDisplayed) {

        adminCoinService.updateDisplaySetting(coinId, isDisplayed);
        return ResponseEntity.ok().build();
    }
}
