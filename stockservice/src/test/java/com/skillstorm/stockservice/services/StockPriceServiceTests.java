package com.skillstorm.stockservice.services;

import com.skillstorm.stockservice.models.StockPrice;
import com.skillstorm.stockservice.repositories.StockPriceRespository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StockPriceServiceTests {

    @Mock
    private StockPriceRespository stockPriceRespository;

    @InjectMocks
    private StockPriceService stockPriceService;

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
    public void testGetAllStockPrices_Success() {
        List<StockPrice> stockPrices = Arrays.asList(new StockPrice(), new StockPrice());
        when(stockPriceRespository.findAll()).thenReturn(stockPrices);

        List<StockPrice> result = stockPriceService.getAllStockPrices();

        assertEquals(stockPrices, result);
        verify(stockPriceRespository, times(1)).findAll();
    }

    @Test
    public void testGetStockPriceById_Success() {
        int stockPriceId = 1;
        StockPrice stockPrice = new StockPrice();
        when(stockPriceRespository.findById(stockPriceId)).thenReturn(Optional.of(stockPrice));

        StockPrice result = stockPriceService.getStockPriceById(stockPriceId);

        assertEquals(stockPrice, result);
        verify(stockPriceRespository, times(1)).findById(stockPriceId);
    }

    @Test
    public void testGetStockPriceById_NotFound() {
        int stockPriceId = 1;
        when(stockPriceRespository.findById(stockPriceId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> stockPriceService.getStockPriceById(stockPriceId));
        assertEquals(NoSuchElementException.class, exception.getClass());
        verify(stockPriceRespository, times(1)).findById(stockPriceId);
    }

    @Test
    public void testGetStockPriceBySymbol_Success() {
        String symbol = "AAPL";
        List<StockPrice> stockPrices = Arrays.asList(new StockPrice(), new StockPrice());
        when(stockPriceRespository.findByStock_Symbol(symbol)).thenReturn(stockPrices);

        List<StockPrice> result = stockPriceService.getStockPriceBySymbol(symbol);

        assertEquals(stockPrices, result);
        verify(stockPriceRespository, times(1)).findByStock_Symbol(symbol);
    }

    @Test
    public void testGetStockPriceBySymbol_NotFound() {
        String symbol = "AAPL";
        when(stockPriceRespository.findByStock_Symbol(symbol)).thenReturn(List.of());

        List<StockPrice> result = stockPriceService.getStockPriceBySymbol(symbol);

        assertNull(result);
        verify(stockPriceRespository, times(1)).findByStock_Symbol(symbol);
    }

    @Test
    public void testSave_Success() {
        StockPrice stockPrice = new StockPrice();
        when(stockPriceRespository.save(any(StockPrice.class))).thenReturn(stockPrice);

        StockPrice result = stockPriceService.save(stockPrice);

        assertEquals(stockPrice, result);
        verify(stockPriceRespository, times(1)).save(stockPrice);
    }

    @Test
    public void testDeleteById_Success() {
        int stockPriceId = 1;
        doNothing().when(stockPriceRespository).deleteById(stockPriceId);

        stockPriceService.deleteById(stockPriceId);

        verify(stockPriceRespository, times(1)).deleteById(stockPriceId);
    }
}
