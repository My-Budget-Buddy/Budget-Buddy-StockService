package com.skillstorm.stockservice.controllers;

import com.skillstorm.stockservice.models.Stock;
import com.skillstorm.stockservice.models.StockPrice;
import com.skillstorm.stockservice.services.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stockPrice")
public class StockPriceController {

    @Autowired
    private StockPriceService stockPriceService;

    @GetMapping
    public ResponseEntity<List<StockPrice>> getAllStockPrices(){
        List<StockPrice> stockPrices = stockPriceService.getAllStockPrices();
        return new ResponseEntity<>(stockPrices, HttpStatus.OK);
    }

    // get stock information by id
    @GetMapping("/{id}")
    public ResponseEntity<StockPrice> getStockPriceById(@PathVariable int id){
        StockPrice stockPrice = stockPriceService.getStockPriceById(id);
        if(stockPrice != null){
            return new ResponseEntity<>(stockPrice, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // get all stock information by symbol
    @GetMapping("/symbol/{symbol}")
    public ResponseEntity<List<StockPrice>> getStockPricesBySymbol(@PathVariable String symbol){
        List<StockPrice> stockPriceList = stockPriceService.getStockPriceBySymbol(symbol);
        if(!stockPriceList.isEmpty()){
            return new ResponseEntity<>(stockPriceList, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // create stock information
    @PostMapping
    public ResponseEntity<StockPrice> createStockPrice(@RequestBody StockPrice stockPrice){
        StockPrice createdStockPrice = stockPriceService.save(stockPrice);
        return new ResponseEntity<>(createdStockPrice, HttpStatus.CREATED);
    }

    // Delete stock information by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockPrice(@PathVariable int id){
        if (stockPriceService.getStockPriceById(id) != null){
            try {
                stockPriceService.deleteById(id);
            } catch (Exception e) {
                e.getMessage();
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
