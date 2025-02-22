package com.cryptory.be.coin.domain;

import com.cryptory.be.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coins")
public class Coin extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;      // 한글 이름(비트코인, 이더리움)

    private boolean isDisplayed;

    private String code;    // 마켓 코드(KRW-BTC, KRW-ETH, KRW-XRP)

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coin_symbol_id")
    private CoinSymbol coinSymbol;  // 코인 심볼 관련 정보

    @Builder
    public Coin(String name, String code, CoinSymbol coinSymbol) {
        this.name = name;
        this.code = code;
        this.coinSymbol = coinSymbol;

        this.isDisplayed = true;
    }
}