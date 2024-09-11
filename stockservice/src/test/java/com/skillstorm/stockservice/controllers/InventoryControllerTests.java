package com.skillstorm.stockservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.stockservice.models.Inventory;
import com.skillstorm.stockservice.services.InventoryService;
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
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
public class InventoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

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
    public void testAddStockToInventory() throws Exception {
        Inventory inventory = new Inventory(1, 1, null, 10, new BigDecimal("100.00"), null);

        when(inventoryService.addStockToInventory(eq(1), eq("AAPL"), eq(10))).thenReturn(inventory);

        mockMvc.perform(post("/inventory/add")
                        .param("userId", "1")
                        .param("stockSymbol", "AAPL")
                        .param("quantity", "10"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    public void testAddStockToInventory_BadRequest() throws Exception {
        when(inventoryService.addStockToInventory(eq(1), eq("AAPL"), eq(10))).thenThrow(new Exception("Bad request"));

        mockMvc.perform(post("/inventory/add")
                        .param("userId", "1")
                        .param("stockSymbol", "AAPL")
                        .param("quantity", "10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUserInventory() throws Exception {
        List<Inventory> inventories = Arrays.asList(
                new Inventory(1, 1, null, 10, new BigDecimal("100.00"), null),
                new Inventory(2, 1, null, 5, new BigDecimal("50.00"), null)
        );

        when(inventoryService.getUserInventory(1)).thenReturn(inventories);

        mockMvc.perform(get("/inventory/{userId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[1].userId").value(1));
    }

    @Test
    public void testGetUserInventory_NotFound() throws Exception {
        when(inventoryService.getUserInventory(1)).thenReturn(List.of());

        mockMvc.perform(get("/inventory/{userId}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetInventoryItemById() throws Exception {
        Inventory inventory = new Inventory(1, 1, null, 10, new BigDecimal("100.00"), null);

        when(inventoryService.getInventoryItemById(1)).thenReturn(inventory);

        mockMvc.perform(get("/inventory/item/{inventoryId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userId").value(1));
    }

    @Test
    public void testGetInventoryItemById_NotFound() throws Exception {
        when(inventoryService.getInventoryItemById(1)).thenReturn(null);

        mockMvc.perform(get("/inventory/item/{inventoryId}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateInventoryItem() throws Exception {
        Inventory updatedInventory = new Inventory(1, 1, null, 20, new BigDecimal("200.00"), null);

        when(inventoryService.updateInventoryItem(eq(1), eq(20), eq(new BigDecimal("200.00")))).thenReturn(updatedInventory);

        mockMvc.perform(put("/inventory/update/{inventoryId}", 1)
                        .param("quantity", "20")
                        .param("purchasePrice", "200.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.quantity").value(20))
                .andExpect(jsonPath("$.purchasePrice").value(200.00));
    }

    @Test
    public void testUpdateInventoryItem_NotFound() throws Exception {
        when(inventoryService.updateInventoryItem(eq(1), eq(20), eq(new BigDecimal("200.00")))).thenReturn(null);

        mockMvc.perform(put("/inventory/update/{inventoryId}", 1)
                        .param("quantity", "20")
                        .param("purchasePrice", "200.00"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteInventoryById() throws Exception {
        doNothing().when(inventoryService).deleteInventoryById(1);

        mockMvc.perform(delete("/inventory/delete/{inventoryId}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteInventoryById_NotFound() throws Exception {
        // Correctly mock the void method to throw an exception using doThrow
        doThrow(new Exception("Not found")).when(inventoryService).deleteInventoryById(1);

        mockMvc.perform(delete("/inventory/delete/{inventoryId}", 1))
                .andExpect(status().isNotFound());
    }
}
