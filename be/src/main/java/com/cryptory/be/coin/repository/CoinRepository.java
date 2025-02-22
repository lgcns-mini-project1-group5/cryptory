package com.cryptory.be.coin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cryptory.be.coin.domain.Coin;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {

}
