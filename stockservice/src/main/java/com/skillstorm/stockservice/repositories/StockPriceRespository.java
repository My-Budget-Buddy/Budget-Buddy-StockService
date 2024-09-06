package com.skillstorm.stockservice.repositories;

import com.skillstorm.stockservice.models.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPriceRespository extends JpaRepository<StockPrice, Integer> {
}
