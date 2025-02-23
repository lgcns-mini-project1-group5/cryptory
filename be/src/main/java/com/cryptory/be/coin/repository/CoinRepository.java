package com.cryptory.be.coin.repository;

import com.cryptory.be.coin.domain.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {

    Coin findByCode(String market);
}
