package com.skillstorm.stockservice.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.skillstorm.stockservice.models.Inventory;
import com.skillstorm.stockservice.models.StockPrice;
import com.skillstorm.stockservice.repositories.InventoryRepository;
import com.skillstorm.stockservice.repositories.StockPriceRespository;
import com.skillstorm.stockservice.repositories.StockRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class InventoryServiceTests {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private StockPriceRespository stockPriceRepository;

    @InjectMocks
    private InventoryService inventoryService;

    private AutoCloseable closeable;

    @BeforeEach
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testAddStockToInventory_Success() throws Exception {
        int userId = 1;
        String stockSymbol = "AAPL";
        int quantity = 10;
        StockPrice stockPrice = new StockPrice();
        stockPrice.setCurrentPrice(BigDecimal.valueOf(150));
        when(stockPriceRepository.findLatestByStockSymbol(stockSymbol)).thenReturn(Arrays.asList(stockPrice));

        Inventory inventory = new Inventory();
        inventory.setUserId(userId);
        inventory.setStockPrice(stockPrice);
        inventory.setQuantity(quantity);
        inventory.setPurchasePrice(stockPrice.getCurrentPrice());
        inventory.setPurchaseDate(LocalDateTime.now());

        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);

        Inventory result = inventoryService.addStockToInventory(userId, stockSymbol, quantity);

        assertEquals(userId, result.getUserId());
        assertEquals(stockPrice, result.getStockPrice());
        assertEquals(quantity, result.getQuantity());
        assertEquals(stockPrice.getCurrentPrice(), result.getPurchasePrice());
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    public void testAddStockToInventory_StockNotFound() {
        String stockSymbol = "AAPL";
        when(stockPriceRepository.findLatestByStockSymbol(stockSymbol)).thenReturn(List.of());

        Exception exception = assertThrows(Exception.class, () -> inventoryService.addStockToInventory(1, stockSymbol, 10));
        assertEquals("Stock with symbol: AAPL not found.", exception.getMessage());
    }

    @Test
    public void testGetUserInventory_Success() {
        int userId = 1;
        List<Inventory> inventoryList = Arrays.asList(new Inventory(), new Inventory());
        when(inventoryRepository.findByUserId(userId)).thenReturn(inventoryList);

        List<Inventory> result = inventoryService.getUserInventory(userId);

        assertEquals(inventoryList, result);
        verify(inventoryRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void testGetInventoryItemById_Success() {
        int inventoryId = 1;
        Inventory inventory = new Inventory();
        when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.of(inventory));

        Inventory result = inventoryService.getInventoryItemById(inventoryId);

        assertEquals(inventory, result);
        verify(inventoryRepository, times(1)).findById(inventoryId);
    }

    @Test
    public void testGetInventoryItemById_NotFound() {
        int inventoryId = 1;
        when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.empty());

        Inventory result = inventoryService.getInventoryItemById(inventoryId);

        assertNull(result);
        verify(inventoryRepository, times(1)).findById(inventoryId);
    }

    @Test
    public void testUpdateInventoryItem_Success() {
        int inventoryId = 1;
        int quantity = 20;
        BigDecimal purchasePrice = BigDecimal.valueOf(200);
        Inventory existingInventory = new Inventory();
        when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.of(existingInventory));

        existingInventory.setQuantity(quantity);
        existingInventory.setPurchasePrice(purchasePrice);
        when(inventoryRepository.save(existingInventory)).thenReturn(existingInventory);

        Inventory result = inventoryService.updateInventoryItem(inventoryId, quantity, purchasePrice);

        assertEquals(quantity, result.getQuantity());
        assertEquals(purchasePrice, result.getPurchasePrice());
        verify(inventoryRepository, times(1)).findById(inventoryId);
        verify(inventoryRepository, times(1)).save(existingInventory);
    }

    @Test
    public void testUpdateInventoryItem_NotFound() {
        int inventoryId = 1;
        when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.empty());

        Inventory result = inventoryService.updateInventoryItem(inventoryId, 20, BigDecimal.valueOf(200));

        assertNull(result);
        verify(inventoryRepository, times(1)).findById(inventoryId);
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    public void testDeleteInventoryById_Success() throws Exception {
        int inventoryId = 1;
        when(inventoryRepository.existsById(inventoryId)).thenReturn(true);

        inventoryService.deleteInventoryById(inventoryId);

        verify(inventoryRepository, times(1)).deleteById(inventoryId);
    }

    @Test
    public void testDeleteInventoryById_NotFound() {
        int inventoryId = 1;
        when(inventoryRepository.existsById(inventoryId)).thenReturn(false);

        Exception exception = assertThrows(Exception.class, () -> inventoryService.deleteInventoryById(inventoryId));
        assertEquals("Inventory item with ID: 1 not found.", exception.getMessage());
        verify(inventoryRepository, never()).deleteById(inventoryId);
    }
}
