package com.skillstorm.stockservice.services;

import com.skillstorm.stockservice.models.Stock;
import com.skillstorm.stockservice.models.StockDataResponse;
import com.skillstorm.stockservice.models.StockPrice;
import com.skillstorm.stockservice.repositories.StockPriceRespository;
import com.skillstorm.stockservice.repositories.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class StockServiceTests {

    @Mock
    private StockPriceRespository stockPriceRespository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private StockService stockService;

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
    public void testGetAllStocks_Success() {
        List<Stock> stocks = Arrays.asList(new Stock(), new Stock());
        when(stockRepository.findAll()).thenReturn(stocks);

        List<Stock> result = stockService.getAllStocks();

        assertEquals(stocks, result);
        verify(stockRepository, times(1)).findAll();
    }

    @Test
    public void testGetStockById_Success() {
        int stockId = 1;
        Stock stock = new Stock();
        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));

        Stock result = stockService.getStockById(stockId);

        assertEquals(stock, result);
        verify(stockRepository, times(1)).findById(stockId);
    }

    @Test
    public void testGetStockById_NotFound() {
        int stockId = 1;
        when(stockRepository.findById(stockId)).thenReturn(Optional.empty());

        Stock result = stockService.getStockById(stockId);

        assertNull(result);
        verify(stockRepository, times(1)).findById(stockId);
    }

    @Test
    public void testGetStockBySymbol_Success() {
        String symbol = "AAPL";
        Stock stock = new Stock();
        when(stockRepository.findBySymbol(symbol)).thenReturn(stock);

        Stock result = stockService.getStockBySymbol(symbol);

        assertEquals(stock, result);
        verify(stockRepository, times(1)).findBySymbol(symbol);
    }

    @Test
    public void testGetStockBySymbol_NotFound() {
        String symbol = "AAPL";
        when(stockRepository.findBySymbol(symbol)).thenReturn(null);

        Stock result = stockService.getStockBySymbol(symbol);

        assertNull(result);
        verify(stockRepository, times(1)).findBySymbol(symbol);
    }

    @Test
    public void testUpdateStock_Success() {
        int stockId = 1;
        Stock existingStock = new Stock();
        existingStock.setSymbol("AAPL");
        Stock updatedStock = new Stock();
        updatedStock.setSymbol("GOOG");
        updatedStock.setCompanyName("Google Inc.");
        when(stockRepository.findById(stockId)).thenReturn(Optional.of(existingStock));
        when(stockRepository.save(existingStock)).thenReturn(existingStock);

        Stock result = stockService.updateStock(stockId, updatedStock);

        assertEquals(updatedStock.getSymbol(), result.getSymbol());
        assertEquals(updatedStock.getCompanyName(), result.getCompanyName());
        verify(stockRepository, times(1)).findById(stockId);
        verify(stockRepository, times(1)).save(existingStock);
    }

    @Test
    public void testUpdateStock_NotFound() {
        int stockId = 1;
        Stock updatedStock = new Stock();
        when(stockRepository.findById(stockId)).thenReturn(Optional.empty());

        Stock result = stockService.updateStock(stockId, updatedStock);

        assertNull(result);
        verify(stockRepository, times(1)).findById(stockId);
        verify(stockRepository, never()).save(any(Stock.class));
    }

    @Test
    public void testCreateStock_Success() {
        Stock stock = new Stock();
        when(stockRepository.save(stock)).thenReturn(stock);

        Stock result = stockService.createStock(stock);

        assertEquals(stock, result);
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    public void testDeleteStock_Success() throws Exception {
        int stockId = 1;
        when(stockRepository.existsById(stockId)).thenReturn(true);

        stockService.deleteStock(stockId);

        verify(stockRepository, times(1)).deleteById(stockId);
    }

    @Test
    public void testDeleteStock_NotFound() {
        int stockId = 1;
        when(stockRepository.existsById(stockId)).thenReturn(false);

        Exception exception = assertThrows(Exception.class, () -> stockService.deleteStock(stockId));
        assertEquals("Stock with id: 1 not found", exception.getMessage());
        verify(stockRepository, never()).deleteById(stockId);
    }

    @Test
    public void testUpdateStockData_Success() throws Exception {
        String symbol = "AAPL";
        StockDataResponse response = new StockDataResponse();
        Map<String, String> quoteData = new HashMap<>();
        quoteData.put("02. open", "100.00");
        quoteData.put("03. high", "110.00");
        quoteData.put("04. low", "90.00");
        quoteData.put("05. price", "105.00");
        quoteData.put("06. volume", "1000");
        quoteData.put("07. latest trading day", "2023-09-01");
        quoteData.put("08. previous close", "102.00");
        quoteData.put("09. change", "3.00");
        quoteData.put("10. change percent", "2.94%");
        response.setGlobalQuote(quoteData);

        when(restTemplate.getForObject(any(String.class), eq(StockDataResponse.class))).thenReturn(response);
        Stock stock = new Stock();
        when(stockRepository.findBySymbol(symbol)).thenReturn(stock);
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);
        when(stockPriceRespository.save(any(StockPrice.class))).thenReturn(new StockPrice());

        stockService.updateStockData(symbol);

        verify(stockRepository, times(1)).findBySymbol(symbol);
        verify(stockPriceRespository, times(1)).save(any(StockPrice.class));
    }

    @Test
    public void testUpdateStockData_StockNotFound() throws Exception {
        String symbol = "AAPL";
        StockDataResponse response = new StockDataResponse();
        Map<String, String> quoteData = new HashMap<>();
        quoteData.put("02. open", "100.00");
        quoteData.put("03. high", "110.00");
        quoteData.put("04. low", "90.00");
        quoteData.put("05. price", "105.00");
        quoteData.put("06. volume", "1000");
        quoteData.put("07. latest trading day", "2023-09-01");
        quoteData.put("08. previous close", "102.00");
        quoteData.put("09. change", "3.00");
        quoteData.put("10. change percent", "2.94%");
        response.setGlobalQuote(quoteData);

        when(restTemplate.getForObject(any(String.class), eq(StockDataResponse.class))).thenReturn(response);
        when(stockRepository.findBySymbol(symbol)).thenReturn(null);

        stockService.updateStockData(symbol);

        verify(stockRepository, times(1)).findBySymbol(symbol);
        verify(stockRepository, times(1)).save(any(Stock.class));
        verify(stockPriceRespository, times(1)).save(any(StockPrice.class));
    }

}
