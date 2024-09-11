package com.skillstorm.stockservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.stockservice.models.Inventory;
import com.skillstorm.stockservice.models.Stock;
import com.skillstorm.stockservice.models.StockPrice;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    
    
    // Finds inventory by userId and StockPrice
    Optional<Inventory> findByUserIdAndStockPrice(int userId, StockPrice stockPrice);

    // Finds inventory by userId
    List<Inventory> findByUserId(int userId);
}
