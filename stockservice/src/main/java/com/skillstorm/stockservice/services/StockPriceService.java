package com.skillstorm.stockservice.services;

import com.skillstorm.stockservice.models.Stock;
import com.skillstorm.stockservice.models.StockPrice;
import com.skillstorm.stockservice.repositories.StockPriceRespository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockPriceService {

    @Autowired
    private StockPriceRespository stockPriceRespository;

    // get all stock information
    public List<StockPrice> getAllStockPrices(){
        return stockPriceRespository.findAll();
    }

    // get stock information by id
    public StockPrice getStockPriceById(int id){
        Optional<StockPrice> stockPrice = stockPriceRespository.findById(id);
        return stockPrice.get();
    }

    // get all stock information by symbol
    public List<StockPrice> getStockPriceBySymbol(String symbol){
        List<StockPrice> stockPriceList = stockPriceRespository.findByStock_Symbol(symbol);
        if(!stockPriceList.isEmpty()){
            return stockPriceList;
        }
        else{
            return null;
        }
    }

    // create stock information
    @Transactional
    public StockPrice save(StockPrice stockPrice) {
        return stockPriceRespository.save(stockPrice);
    }


    // delete stock information by id
    @Transactional
    public void deleteById(int id) {
        stockPriceRespository.deleteById(id);
    }
}
