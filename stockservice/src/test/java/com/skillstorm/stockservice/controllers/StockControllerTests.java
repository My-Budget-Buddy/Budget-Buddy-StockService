package com.skillstorm.stockservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.stockservice.models.Stock;
import com.skillstorm.stockservice.services.StockService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockController.class)
public class StockControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void testGetAllStocks() throws Exception {
        List<Stock> stocks = Arrays.asList(
                new Stock(1, "AAPL", "Apple Inc.", LocalDateTime.now()),
                new Stock(2, "GOOGL", "Google LLC", LocalDateTime.now())
        );

        when(stockService.getAllStocks()).thenReturn(stocks);

        mockMvc.perform(get("/stocks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].symbol").value("AAPL"))
                .andExpect(jsonPath("$[1].symbol").value("GOOGL"));
    }

    @Test
    public void testGetStockById_Success() throws Exception {
        Stock stock = new Stock(1, "AAPL", "Apple Inc.", LocalDateTime.now());

        when(stockService.getStockById(1)).thenReturn(stock);

        mockMvc.perform(get("/stocks/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.symbol").value("AAPL"));
    }

    @Test
    public void testGetStockById_NotFound() throws Exception {
        when(stockService.getStockById(1)).thenReturn(null);

        mockMvc.perform(get("/stocks/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetStockBySymbol_Success() throws Exception {
        Stock stock = new Stock(1, "AAPL", "Apple Inc.", LocalDateTime.now());

        when(stockService.getStockBySymbol("AAPL")).thenReturn(stock);

        mockMvc.perform(get("/stocks/symbol/{symbol}", "AAPL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("AAPL"))
                .andExpect(jsonPath("$.companyName").value("Apple Inc."));
    }

    @Test
    public void testGetStockBySymbol_NotFound() throws Exception {
        when(stockService.getStockBySymbol("AAPL")).thenReturn(null);

        mockMvc.perform(get("/stocks/symbol/{symbol}", "AAPL"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateStock_Success() throws Exception {
        Stock updatedStock = new Stock(1, "AAPL", "Apple Inc. Updated", LocalDateTime.now());

        when(stockService.updateStock(eq(1), any(Stock.class))).thenReturn(updatedStock);

        mockMvc.perform(put("/stocks/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStock)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("Apple Inc. Updated"));
    }

    @Test
    public void testUpdateStock_NotFound() throws Exception {
        when(stockService.updateStock(eq(1), any(Stock.class))).thenReturn(null);

        mockMvc.perform(put("/stocks/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Stock())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateStock() throws Exception {
        Stock stock = new Stock(1, "AAPL", "Apple Inc.", LocalDateTime.now());

        when(stockService.createStock(any(Stock.class))).thenReturn(stock);

        mockMvc.perform(post("/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stock)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.symbol").value("AAPL"))
                .andExpect(jsonPath("$.companyName").value("Apple Inc."));
    }

    @Test
    public void testDeleteStock_Success() throws Exception {
        doNothing().when(stockService).deleteStock(1);
        when(stockService.getStockById(1)).thenReturn(new Stock());

        mockMvc.perform(delete("/stocks/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteStock_NotFound() throws Exception {
        when(stockService.getStockById(1)).thenReturn(null);

        mockMvc.perform(delete("/stocks/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateStockData_Success() throws Exception {
        doNothing().when(stockService).updateStockData("AAPL");

        mockMvc.perform(post("/stocks/update/{symbol}", "AAPL"))
                .andExpect(status().isOk())
                .andExpect(content().string("Stock data updated successfully"));
    }

    @Test
    public void testUpdateStockData_Error() throws Exception {
        doThrow(new Exception("Update failed")).when(stockService).updateStockData("AAPL");

        mockMvc.perform(post("/stocks/update/{symbol}", "AAPL"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error updating stock data: Update failed"));
    }
}
