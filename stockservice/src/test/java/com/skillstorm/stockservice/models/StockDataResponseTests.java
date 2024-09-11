package com.skillstorm.stockservice.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class StockDataResponseTests {

    private StockDataResponse stockDataResponse;

    @BeforeEach
    public void setup() {
        stockDataResponse = new StockDataResponse();
    }

    @Test
    public void testSetAndGetGlobalQuote() {
        Map<String, String> globalQuote = new HashMap<>();
        globalQuote.put("01. symbol", "AAPL");
        globalQuote.put("02. open", "150.00");

        stockDataResponse.setGlobalQuote(globalQuote);

        assertEquals(globalQuote, stockDataResponse.getGlobalQuote());
    }

    @Test
    public void testJsonPropertyMapping() throws Exception {
        String json = "{ \"Global Quote\": { \"01. symbol\": \"AAPL\", \"02. open\": \"150.00\" } }";

        ObjectMapper objectMapper = new ObjectMapper();
        StockDataResponse response = objectMapper.readValue(json, StockDataResponse.class);

        Map<String, String> expectedGlobalQuote = new HashMap<>();
        expectedGlobalQuote.put("01. symbol", "AAPL");
        expectedGlobalQuote.put("02. open", "150.00");

        assertNotNull(response.getGlobalQuote());
        assertEquals(expectedGlobalQuote, response.getGlobalQuote());
    }

    @Test
    public void testSetGlobalQuoteWithNull() {
        stockDataResponse.setGlobalQuote(null);
        assertNull(stockDataResponse.getGlobalQuote());
    }

    @Test
    public void testGetGlobalQuoteInitiallyNull() {
        assertNull(stockDataResponse.getGlobalQuote());
    }
}
