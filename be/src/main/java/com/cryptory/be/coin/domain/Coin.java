package com.cryptory.be.coin.domain;

import com.cryptory.be.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "coins")
public class Coin extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private String crypto_color;
    
    private boolean is_displayed;
    
    // 심볼 정보
    @OneToOne
    @JoinColumn(name = "symbol_id", unique = true)
    private CoinSymbol coinSymbol;
    
    /*
    // 차트 리스트
    @JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "crypto_id")
	private List<Chart> chartList;
    
    // 게시글 리스트
    @JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "crypto_id")
	private List<Post> postList;
	*/
}
