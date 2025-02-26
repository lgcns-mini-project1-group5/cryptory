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
    // WHERE :keyword IS NULL OR LOWER(c.koreanName) LIKE %:keyword% OR LOWER(c.englishName) LIKE %:keyword%
    @Query("SELECT c FROM Coin c WHERE LOWER(c.koreanName) LIKE :keyword OR LOWER(c.englishName) LIKE :keyword")
    Page<Coin> searchCoins(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT COUNT(i) FROM Coin i WHERE i.isDisplayed = true")
    long countByIsDisplayedTrue();
}
