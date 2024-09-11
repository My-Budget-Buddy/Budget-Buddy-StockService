package com.skillstorm.stockservice.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class StockPriceTests {

    private StockPrice stockPrice;
    private Stock stock;

    @BeforeEach
    public void setup() {
        stock = new Stock(1, "AAPL", "Apple Inc.", LocalDate.now().atStartOfDay());
        stockPrice = new StockPrice();
    }

    @Test
    public void testSetAndGetId() {
        int id = 1;
        stockPrice.setId(id);
        assertEquals(id, stockPrice.getId());
    }

    @Test
    public void testSetAndGetStock() {
        stockPrice.setStock(stock);
        assertEquals(stock, stockPrice.getStock());
    }

    @Test
    public void testSetAndGetOpenPrice() {
        BigDecimal openPrice = new BigDecimal("150.00");
        stockPrice.setOpenPrice(openPrice);
        assertEquals(openPrice, stockPrice.getOpenPrice());
    }

    @Test
    public void testSetAndGetHighPrice() {
        BigDecimal highPrice = new BigDecimal("155.00");
        stockPrice.setHighPrice(highPrice);
        assertEquals(highPrice, stockPrice.getHighPrice());
    }

    @Test
    public void testSetAndGetLowPrice() {
        BigDecimal lowPrice = new BigDecimal("145.00");
        stockPrice.setLowPrice(lowPrice);
        assertEquals(lowPrice, stockPrice.getLowPrice());
    }

    @Test
    public void testSetAndGetCurrentPrice() {
        BigDecimal currentPrice = new BigDecimal("152.00");
        stockPrice.setCurrentPrice(currentPrice);
        assertEquals(currentPrice, stockPrice.getCurrentPrice());
    }

    @Test
    public void testSetAndGetVolume() {
        Long volume = 1000000L;
        stockPrice.setVolume(volume);
        assertEquals(volume, stockPrice.getVolume());
    }

    @Test
    public void testSetAndGetPreviousClose() {
        BigDecimal previousClose = new BigDecimal("148.00");
        stockPrice.setPreviousClose(previousClose);
        assertEquals(previousClose, stockPrice.getPreviousClose());
    }

    @Test
    public void testSetAndGetPriceChange() {
        BigDecimal priceChange = new BigDecimal("4.00");
        stockPrice.setPriceChange(priceChange);
        assertEquals(priceChange, stockPrice.getPriceChange());
    }

    @Test
    public void testSetAndGetChangePercent() {
        BigDecimal changePercent = new BigDecimal("2.70");
        stockPrice.setChangePercent(changePercent);
        assertEquals(changePercent, stockPrice.getChangePercent());
    }

    @Test
    public void testSetAndGetTradingDay() {
        LocalDate tradingDay = LocalDate.now();
        stockPrice.setTradingDay(tradingDay);
        assertEquals(tradingDay, stockPrice.getTradingDay());
    }

    @Test
    public void testConstructorWithoutId() {
        LocalDate tradingDay = LocalDate.now();
        StockPrice stockPrice = new StockPrice(stock, new BigDecimal("150.00"), new BigDecimal("155.00"),
                new BigDecimal("145.00"), new BigDecimal("152.00"), 1000000L, new BigDecimal("148.00"),
                new BigDecimal("4.00"), new BigDecimal("2.70"), tradingDay);

        assertEquals(stock, stockPrice.getStock());
        assertEquals(new BigDecimal("150.00"), stockPrice.getOpenPrice());
        assertEquals(new BigDecimal("155.00"), stockPrice.getHighPrice());
        assertEquals(new BigDecimal("145.00"), stockPrice.getLowPrice());
        assertEquals(new BigDecimal("152.00"), stockPrice.getCurrentPrice());
        assertEquals(1000000L, stockPrice.getVolume());
        assertEquals(new BigDecimal("148.00"), stockPrice.getPreviousClose());
        assertEquals(new BigDecimal("4.00"), stockPrice.getPriceChange());
        assertEquals(new BigDecimal("2.70"), stockPrice.getChangePercent());
        assertEquals(tradingDay, stockPrice.getTradingDay());
    }

    @Test
    public void testConstructorWithId() {
        LocalDate tradingDay = LocalDate.now();
        StockPrice stockPrice = new StockPrice(1, stock, new BigDecimal("150.00"), new BigDecimal("155.00"),
                new BigDecimal("145.00"), new BigDecimal("152.00"), 1000000L, new BigDecimal("148.00"),
                new BigDecimal("4.00"), new BigDecimal("2.70"), tradingDay);

        assertEquals(1, stockPrice.getId());
        assertEquals(stock, stockPrice.getStock());
        assertEquals(new BigDecimal("150.00"), stockPrice.getOpenPrice());
        assertEquals(new BigDecimal("155.00"), stockPrice.getHighPrice());
        assertEquals(new BigDecimal("145.00"), stockPrice.getLowPrice());
        assertEquals(new BigDecimal("152.00"), stockPrice.getCurrentPrice());
        assertEquals(1000000L, stockPrice.getVolume());
        assertEquals(new BigDecimal("148.00"), stockPrice.getPreviousClose());
        assertEquals(new BigDecimal("4.00"), stockPrice.getPriceChange());
        assertEquals(new BigDecimal("2.70"), stockPrice.getChangePercent());
        assertEquals(tradingDay, stockPrice.getTradingDay());
    }

    @Test
    public void testEquals() {
        LocalDate tradingDay = LocalDate.now();
        StockPrice stockPrice1 = new StockPrice(1, stock, new BigDecimal("150.00"), new BigDecimal("155.00"),
                new BigDecimal("145.00"), new BigDecimal("152.00"), 1000000L, new BigDecimal("148.00"),
                new BigDecimal("4.00"), new BigDecimal("2.70"), tradingDay);
        StockPrice stockPrice2 = new StockPrice(1, stock, new BigDecimal("150.00"), new BigDecimal("155.00"),
                new BigDecimal("145.00"), new BigDecimal("152.00"), 1000000L, new BigDecimal("148.00"),
                new BigDecimal("4.00"), new BigDecimal("2.70"), tradingDay);

        assertEquals(stockPrice1, stockPrice2);

        stockPrice2.setId(2);

        assertNotEquals(stockPrice1, stockPrice2);
    }

    @Test
    public void testHashCode() {
        LocalDate tradingDay = LocalDate.now();
        StockPrice stockPrice1 = new StockPrice(1, stock, new BigDecimal("150.00"), new BigDecimal("155.00"),
                new BigDecimal("145.00"), new BigDecimal("152.00"), 1000000L, new BigDecimal("148.00"),
                new BigDecimal("4.00"), new BigDecimal("2.70"), tradingDay);
        StockPrice stockPrice2 = new StockPrice(1, stock, new BigDecimal("150.00"), new BigDecimal("155.00"),
                new BigDecimal("145.00"), new BigDecimal("152.00"), 1000000L, new BigDecimal("148.00"),
                new BigDecimal("4.00"), new BigDecimal("2.70"), tradingDay);

        assertEquals(stockPrice1.hashCode(), stockPrice2.hashCode());

        stockPrice2.setId(2);

        assertNotEquals(stockPrice1.hashCode(), stockPrice2.hashCode());
    }

    @Test
    public void testToString() {
        LocalDate tradingDay = LocalDate.now();
        StockPrice stockPrice = new StockPrice(1, stock, new BigDecimal("150.00"), new BigDecimal("155.00"),
                new BigDecimal("145.00"), new BigDecimal("152.00"), 1000000L, new BigDecimal("148.00"),
                new BigDecimal("4.00"), new BigDecimal("2.70"), tradingDay);

        String actual = stockPrice.toString();

        // Check that the major components of the toString are as expected
        assertTrue(actual.contains("StockPrice [id=1"));
        assertTrue(actual.contains("stock=" + stock.toString()));
        assertTrue(actual.contains("openPrice=150.00"));
        assertTrue(actual.contains("highPrice=155.00"));
        assertTrue(actual.contains("lowPrice=145.00"));
        assertTrue(actual.contains("currentPrice=152.00"));
        assertTrue(actual.contains("volume=1000000"));
        assertTrue(actual.contains("previousClose=148.00"));
        assertTrue(actual.contains("priceChange=4.00"));
        assertTrue(actual.contains("changePercent=2.70"));
        assertTrue(actual.contains("tradingDay=" + tradingDay.toString()));
    }
}
