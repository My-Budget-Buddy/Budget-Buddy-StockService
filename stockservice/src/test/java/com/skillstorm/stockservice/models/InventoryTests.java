package com.skillstorm.stockservice.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTests {

    private Inventory inventory;

    @BeforeEach
    public void setup() {
        inventory = new Inventory();
    }

    @Test
    public void testSetAndGetId() {
        int id = 1;
        inventory.setId(id);
        assertEquals(id, inventory.getId());
    }

    @Test
    public void testSetAndGetUserId() {
        int userId = 1;
        inventory.setUserId(userId);
        assertEquals(userId, inventory.getUserId());
    }

    @Test
    public void testSetAndGetStockPrice() {
        StockPrice stockPrice = new StockPrice();
        stockPrice.setId(1);
        inventory.setStockPrice(stockPrice);
        assertEquals(stockPrice, inventory.getStockPrice());
    }

    @Test
    public void testSetAndGetQuantity() {
        int quantity = 10;
        inventory.setQuantity(quantity);
        assertEquals(quantity, inventory.getQuantity());
    }

    @Test
    public void testSetAndGetPurchasePrice() {
        BigDecimal purchasePrice = new BigDecimal("100.00");
        inventory.setPurchasePrice(purchasePrice);
        assertEquals(purchasePrice, inventory.getPurchasePrice());
    }

    @Test
    public void testSetAndGetPurchaseDate() {
        LocalDateTime purchaseDate = LocalDateTime.now();
        inventory.setPurchaseDate(purchaseDate);
        assertEquals(purchaseDate, inventory.getPurchaseDate());
    }

    @Test
    public void testInventoryConstructorAndGetters() {
        int userId = 1;
        StockPrice stockPrice = new StockPrice();
        stockPrice.setId(1);
        int quantity = 10;
        BigDecimal purchasePrice = new BigDecimal("100.00");
        LocalDateTime purchaseDate = LocalDateTime.now();

        Inventory inventory = new Inventory(userId, stockPrice, quantity, purchasePrice, purchaseDate);

        assertEquals(userId, inventory.getUserId());
        assertEquals(stockPrice, inventory.getStockPrice());
        assertEquals(quantity, inventory.getQuantity());
        assertEquals(purchasePrice, inventory.getPurchasePrice());
        assertEquals(purchaseDate, inventory.getPurchaseDate());
    }

    @Test
    public void testInventoryConstructorWithIdAndGetters() {
        int id = 1;
        int userId = 1;
        StockPrice stockPrice = new StockPrice();
        stockPrice.setId(1);
        int quantity = 10;
        BigDecimal purchasePrice = new BigDecimal("100.00");
        LocalDateTime purchaseDate = LocalDateTime.now();

        Inventory inventory = new Inventory(id, userId, stockPrice, quantity, purchasePrice, purchaseDate);

        assertEquals(id, inventory.getId());
        assertEquals(userId, inventory.getUserId());
        assertEquals(stockPrice, inventory.getStockPrice());
        assertEquals(quantity, inventory.getQuantity());
        assertEquals(purchasePrice, inventory.getPurchasePrice());
        assertEquals(purchaseDate, inventory.getPurchaseDate());
    }

    @Test
    public void testEquals() {
        LocalDateTime purchaseDate = LocalDateTime.now();
        StockPrice stockPrice = new StockPrice();
        stockPrice.setId(1);
        Inventory inventory1 = new Inventory(1, 1, stockPrice, 10, new BigDecimal("100.00"), purchaseDate);
        Inventory inventory2 = new Inventory(1, 1, stockPrice, 10, new BigDecimal("100.00"), purchaseDate);

        assertEquals(inventory1, inventory2);

        inventory2.setId(2);

        assertNotEquals(inventory1, inventory2);
    }

    @Test
    public void testHashCode() {
        LocalDateTime purchaseDate = LocalDateTime.now();
        StockPrice stockPrice = new StockPrice();
        stockPrice.setId(1);
        Inventory inventory1 = new Inventory(1, 1, stockPrice, 10, new BigDecimal("100.00"), purchaseDate);
        Inventory inventory2 = new Inventory(1, 1, stockPrice, 10, new BigDecimal("100.00"), purchaseDate);

        assertEquals(inventory1.hashCode(), inventory2.hashCode());

        inventory2.setId(2);

        assertNotEquals(inventory1.hashCode(), inventory2.hashCode());
    }

    @Test
    public void testToString() {
        LocalDateTime purchaseDate = LocalDateTime.now();
        StockPrice stockPrice = new StockPrice();
        stockPrice.setId(1);
        Inventory inventory = new Inventory(1, 1, stockPrice, 10, new BigDecimal("100.00"), purchaseDate);

        String actual = inventory.toString();

        // Check that the major components of the toString are as expected
        assertTrue(actual.contains("Inventory [id=1"));
        assertTrue(actual.contains("userId=1"));
        assertTrue(actual.contains("stockPrice=StockPrice [id=1"));
        assertTrue(actual.contains("quantity=10"));
        assertTrue(actual.contains("purchasePrice=100.00"));
        assertTrue(actual.contains("purchaseDate=" + purchaseDate.toString()));
    }
}
