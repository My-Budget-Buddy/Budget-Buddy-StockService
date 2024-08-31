package com.skillstorm.stockservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.stockservice.models.Inventory;
import com.skillstorm.stockservice.models.Stock;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    
    List<Inventory> findByUserId(int userId);
    Optional<Inventory> findByUserIdAndStock(int userId, Stock stock);
}
