package com.skillstorm.stockservice.repositories;

import com.skillstorm.stockservice.models.Stock;
import com.skillstorm.stockservice.models.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockPriceRespository extends JpaRepository<StockPrice, Integer> {

    List<StockPrice> findByStock_Symbol(String stockSymbol);

    @Query("SELECT sp FROM StockPrice sp WHERE sp.stock.symbol = :stockSymbol ORDER BY sp.tradingDay DESC")
    List<StockPrice> findLatestByStockSymbol(@Param("stockSymbol") String stockSymbol);
}
