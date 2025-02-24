package com.cryptory.be.admin.dto.dashboard;

/**
 * packageName    : com.cryptory.be.admin.dto.dashboard
 * fileName       : CoinTrafficDto
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class CoinTrafficDto {
    private Long coinId;
    private String coinName;
    private String symbol;
    private Long traffic;
}
