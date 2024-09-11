package com.skillstorm.stockservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.stockservice.models.Stock;
import com.skillstorm.stockservice.models.StockPrice;
import com.skillstorm.stockservice.services.StockPriceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockPriceController.class)
public class StockPriceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockPriceService stockPriceService;

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
    public void testGetAllStockPrices() throws Exception {
        List<StockPrice> stockPrices = Arrays.asList(
                new StockPrice(new Stock(), new BigDecimal("100"), new BigDecimal("110"),
                        new BigDecimal("90"), new BigDecimal("105"), 1000L, new BigDecimal("95"),
                        new BigDecimal("10"), new BigDecimal("9.5"), LocalDate.now()),
                new StockPrice(new Stock(), new BigDecimal("200"), new BigDecimal("210"),
                        new BigDecimal("190"), new BigDecimal("205"), 2000L, new BigDecimal("195"),
                        new BigDecimal("10"), new BigDecimal("4.9"), LocalDate.now())
        );

        when(stockPriceService.getAllStockPrices()).thenReturn(stockPrices);

        mockMvc.perform(get("/stockPrice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].currentPrice").value(105))
                .andExpect(jsonPath("$[1].currentPrice").value(205));
    }

    @Test
    public void testGetStockPriceById_Success() throws Exception {
        StockPrice stockPrice = new StockPrice(new Stock(), new BigDecimal("100"), new BigDecimal("110"),
                new BigDecimal("90"), new BigDecimal("105"), 1000L, new BigDecimal("95"),
                new BigDecimal("10"), new BigDecimal("9.5"), LocalDate.now());
        stockPrice.setId(1);

        when(stockPriceService.getStockPriceById(1)).thenReturn(stockPrice);

        mockMvc.perform(get("/stockPrice/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.currentPrice").value(105));
    }

    @Test
    public void testGetStockPriceById_NotFound() throws Exception {
        when(stockPriceService.getStockPriceById(1)).thenReturn(null);

        mockMvc.perform(get("/stockPrice/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetStockPricesBySymbol_Success() throws Exception {
        List<StockPrice> stockPrices = Arrays.asList(
                new StockPrice(new Stock(), new BigDecimal("100"), new BigDecimal("110"),
                        new BigDecimal("90"), new BigDecimal("105"), 1000L, new BigDecimal("95"),
                        new BigDecimal("10"), new BigDecimal("9.5"), LocalDate.now()),
                new StockPrice(new Stock(), new BigDecimal("200"), new BigDecimal("210"),
                        new BigDecimal("190"), new BigDecimal("205"), 2000L, new BigDecimal("195"),
                        new BigDecimal("10"), new BigDecimal("4.9"), LocalDate.now())
        );

        when(stockPriceService.getStockPriceBySymbol("AAPL")).thenReturn(stockPrices);

        mockMvc.perform(get("/stockPrice/symbol/{symbol}", "AAPL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].currentPrice").value(105))
                .andExpect(jsonPath("$[1].currentPrice").value(205));
    }

    @Test
    public void testGetStockPricesBySymbol_NotFound() throws Exception {
        when(stockPriceService.getStockPriceBySymbol("AAPL")).thenReturn(List.of());

        mockMvc.perform(get("/stockPrice/symbol/{symbol}", "AAPL"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateStockPrice() throws Exception {
        StockPrice stockPrice = new StockPrice(new Stock(), new BigDecimal("100"), new BigDecimal("110"),
                new BigDecimal("90"), new BigDecimal("105"), 1000L, new BigDecimal("95"),
                new BigDecimal("10"), new BigDecimal("9.5"), LocalDate.now());

        when(stockPriceService.save(any(StockPrice.class))).thenReturn(stockPrice);

        mockMvc.perform(post("/stockPrice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stockPrice)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.currentPrice").value(105));
    }

    @Test
    public void testDeleteStockPrice_Success() throws Exception {
        doNothing().when(stockPriceService).deleteById(1);
        when(stockPriceService.getStockPriceById(1)).thenReturn(new StockPrice());

        mockMvc.perform(delete("/stockPrice/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteStockPrice_NotFound() throws Exception {
        when(stockPriceService.getStockPriceById(1)).thenReturn(null);

        mockMvc.perform(delete("/stockPrice/{id}", 1))
                .andExpect(status().isNotFound());
    }
}
