package com.skillstorm.stockservice.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.stockservice.models.Inventory;
import com.skillstorm.stockservice.models.Stock;
import com.skillstorm.stockservice.models.StockPrice;
import com.skillstorm.stockservice.repositories.InventoryRepository;
import com.skillstorm.stockservice.repositories.StockPriceRespository;
import com.skillstorm.stockservice.repositories.StockRepository;

import jakarta.transaction.Transactional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockPriceRespository stockPriceRepository;

    // Adds Stock and StockPrice to Inventory using stock symbol
    public Inventory addStockToInventory(int userId, String stockSymbol, Integer quantity) throws Exception {
        // Find the stock by symbol
        StockPrice stockPrice = stockPriceRepository.findByStockOnly_Symbol(stockSymbol);

        if (stockPrice != null) {
            // Create a new inventory item
            Inventory inventory = new Inventory();
            inventory.setUserId(userId);
            inventory.setStockPrice(stockPrice); 
            inventory.setQuantity(quantity);
            inventory.setPurchasePrice(stockPrice.getCurrentPrice());  
            inventory.setPurchaseDate(LocalDateTime.now());
            
            // Save the inventory item
            return inventoryRepository.save(inventory);
        } else {
            throw new Exception("Stock with symbol: " + stockSymbol + " not found.");
        }
    }

    // Get all stocks in user's inventory
    public List<Inventory> getUserInventory(int userId) {
        return inventoryRepository.findByUserId(userId);
    }

    // Get a specific inventory item by its ID (Read)
    public Inventory getInventoryItemById(int inventoryId) {
        Optional<Inventory> inventory = inventoryRepository.findById(inventoryId);
        return inventory.orElse(null);
    }

    // Update an existing inventory item (Update)
    public Inventory updateInventoryItem(int inventoryId, Integer quantity, BigDecimal purchasePrice) {
        Optional<Inventory> existingInventory = inventoryRepository.findById(inventoryId);
        if (existingInventory.isPresent()) {
            Inventory inventory = existingInventory.get();
            inventory.setQuantity(quantity);
            inventory.setPurchasePrice(purchasePrice);
            return inventoryRepository.save(inventory);
        }
        return null;
    }

    // Deletes a stock from the inventory
    public void deleteStockFromInventory(int userId, String stockSymbol) throws Exception {
        Stock stock = stockRepository.findBySymbol(stockSymbol);
        if (stock == null) {
            throw new Exception("Stock with symbol: " + stockSymbol + " not found.");
        }

        Optional<Inventory> inventory = inventoryRepository.findByUserIdAndStock(userId, stock);
        if (inventory.isEmpty()) {
            throw new Exception("Inventory item not found for user ID: " + userId + " and stock symbol: " + stockSymbol);
        }

        inventoryRepository.delete(inventory.get());
    }
}
