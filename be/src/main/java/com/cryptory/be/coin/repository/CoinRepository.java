package com.cryptory.be.coin.repository;

import com.cryptory.be.coin.domain.Coin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {

    Coin findByCode(String market);

    // symbol대신 englishName으로 검색 수정
    @Query("SELECT c FROM Coin c WHERE c.koreanName LIKE %:keyword% OR c.englishName LIKE %:keyword%")
    Page<Coin> searchCoins(@Param("keyword") String keyword, Pageable pageable);

    long countByIsDisplayedTrue();
}
