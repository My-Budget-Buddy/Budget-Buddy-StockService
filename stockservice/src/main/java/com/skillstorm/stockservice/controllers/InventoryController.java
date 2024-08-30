package com.skillstorm.stockservice.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.stockservice.models.Inventory;
import com.skillstorm.stockservice.services.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    
     @Autowired
    private InventoryService inventoryService;

    // Endpoint to add a stock to the inventory
    @PostMapping("/add")
    public ResponseEntity<Inventory> addStockToInventory(
            @RequestParam int userId,
            @RequestParam String stockSymbol,
            @RequestParam Integer quantity,
            @RequestParam BigDecimal purchasePrice) {
        Inventory inventory = inventoryService.addStockToInventory(userId, stockSymbol, quantity, purchasePrice);
        if (inventory != null) {
            return new ResponseEntity<>(inventory, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to get all stocks in the user's inventory
    @GetMapping("/{userId}")
    public ResponseEntity<List<Inventory>> getUserInventory(@PathVariable int userId) {
        List<Inventory> inventoryList = inventoryService.getUserInventory(userId);
        if (inventoryList != null && !inventoryList.isEmpty()) {
            return new ResponseEntity<>(inventoryList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to get a specific inventory item by its ID
    @GetMapping("/item/{inventoryId}")
    public ResponseEntity<Inventory> getInventoryItemById(@PathVariable int inventoryId) {
        Inventory inventory = inventoryService.getInventoryItemById(inventoryId);
        if (inventory != null) {
            return new ResponseEntity<>(inventory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to update an inventory item
    @PutMapping("/update/{inventoryId}")
    public ResponseEntity<Inventory> updateInventoryItem(
            @PathVariable int inventoryId,
            @RequestParam Integer quantity,
            @RequestParam BigDecimal purchasePrice) {
        Inventory updatedInventory = inventoryService.updateInventoryItem(inventoryId, quantity, purchasePrice);
        if (updatedInventory != null) {
            return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to delete a stock from the inventory
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteStockFromInventory(
            @RequestParam int userId,
            @RequestParam String stockSymbol) {
        try {
            inventoryService.deleteStockFromInventory(userId, stockSymbol);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
