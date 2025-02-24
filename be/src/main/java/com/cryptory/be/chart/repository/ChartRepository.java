package com.cryptory.be.chart.repository;

import com.cryptory.be.chart.domain.Chart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChartRepository extends JpaRepository<Chart, Long> {
    List<Chart> findAllByCoinId(Long coinId);

    Optional<Chart> findByDateAndCoinId(String date, Long coinId);
}
