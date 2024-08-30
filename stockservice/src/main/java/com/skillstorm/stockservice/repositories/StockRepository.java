package com.skillstorm.stockservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.stockservice.models.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer>{
    
    Stock findBySymbol(String symbol);
}
