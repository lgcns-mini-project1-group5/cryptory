package com.cryptory.be.admin.dto.dashboard;
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
