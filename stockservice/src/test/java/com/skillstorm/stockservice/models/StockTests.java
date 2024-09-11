package com.skillstorm.stockservice.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class StockTests {

    private Stock stock;

    @BeforeEach
    public void setup() {
        stock = new Stock();
    }

    @Test
    public void testSetAndGetId() {
        int id = 1;
        stock.setId(id);
        assertEquals(id, stock.getId());
    }

    @Test
    public void testSetAndGetSymbol() {
        String symbol = "AAPL";
        stock.setSymbol(symbol);
        assertEquals(symbol, stock.getSymbol());
    }

    @Test
    public void testSetAndGetCompanyName() {
        String companyName = "Apple Inc.";
        stock.setCompanyName(companyName);
        assertEquals(companyName, stock.getCompanyName());
    }

    @Test
    public void testSetAndGetCreatedAt() {
        LocalDateTime createdAt = LocalDateTime.now();
        stock.setCreatedAt(createdAt);
        assertEquals(createdAt, stock.getCreatedAt());
    }

    @Test
    public void testStockConstructorWithoutId() {
        String symbol = "AAPL";
        String companyName = "Apple Inc.";
        LocalDateTime createdAt = LocalDateTime.now();

        Stock stock = new Stock(symbol, companyName, createdAt);

        assertEquals(symbol, stock.getSymbol());
        assertEquals(companyName, stock.getCompanyName());
        assertEquals(createdAt, stock.getCreatedAt());
    }

    @Test
    public void testStockConstructorWithId() {
        int id = 1;
        String symbol = "AAPL";
        String companyName = "Apple Inc.";
        LocalDateTime createdAt = LocalDateTime.now();

        Stock stock = new Stock(id, symbol, companyName, createdAt);

        assertEquals(id, stock.getId());
        assertEquals(symbol, stock.getSymbol());
        assertEquals(companyName, stock.getCompanyName());
        assertEquals(createdAt, stock.getCreatedAt());
    }

    @Test
    public void testEquals() {
        LocalDateTime createdAt = LocalDateTime.now();
        Stock stock1 = new Stock(1, "AAPL", "Apple Inc.", createdAt);
        Stock stock2 = new Stock(1, "AAPL", "Apple Inc.", createdAt);

        assertEquals(stock1, stock2);

        stock2.setId(2);

        assertNotEquals(stock1, stock2);
    }

    @Test
    public void testHashCode() {
        LocalDateTime createdAt = LocalDateTime.now();
        Stock stock1 = new Stock(1, "AAPL", "Apple Inc.", createdAt);
        Stock stock2 = new Stock(1, "AAPL", "Apple Inc.", createdAt);

        assertEquals(stock1.hashCode(), stock2.hashCode());

        stock2.setId(2);

        assertNotEquals(stock1.hashCode(), stock2.hashCode());
    }

    @Test
    public void testToString() {
        LocalDateTime createdAt = LocalDateTime.now();
        Stock stock = new Stock(1, "AAPL", "Apple Inc.", createdAt);

        String actual = stock.toString();

        // Validate that the critical components of the toString output are present
        assertTrue(actual.contains("Stock [id=1"));
        assertTrue(actual.contains("symbol=AAPL"));
        assertTrue(actual.contains("companyName=Apple Inc."));
        assertTrue(actual.contains("createdAt=" + createdAt.toString()));
    }
}
