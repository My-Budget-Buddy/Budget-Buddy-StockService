package com.skillstorm.stockservice.repositories;

import com.skillstorm.stockservice.models.Stock;
import com.skillstorm.stockservice.models.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockPriceRespository extends JpaRepository<StockPrice, Integer> {

    List<StockPrice> findByStock_Symbol(String stockSymbol);

    StockPrice findByStockOnly_Symbol(String stockSymbol);
}
