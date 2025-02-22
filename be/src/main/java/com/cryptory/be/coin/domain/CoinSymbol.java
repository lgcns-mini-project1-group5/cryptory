package com.cryptory.be.coin.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coin_symbols")
public class CoinSymbol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;
    private String logoUrl;

    @OneToOne(mappedBy = "coinSymbol")
    private Coin coin;

    @Builder
    public CoinSymbol(String color, String logoUrl) {
        this.color = color;
        this.logoUrl = logoUrl;
    }

    // TODO 초기 색깔, url 데이터는 어디서? 혹시 몰라서 관리자에서 업데이트할 수 있도록 메소드 추가
    public void updateColorAndLogo(String color, String logoUrl) {
        this.color = color;
        this.logoUrl = logoUrl;
    }
}
