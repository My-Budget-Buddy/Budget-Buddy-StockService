package com.skillstorm.stockservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.stockservice.models.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer>{
    
}
