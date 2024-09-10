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

    public List<StockPrice> getAllStockPrices(){
        return stockPriceRespository.findAll();
    }
    public StockPrice getStockPriceById(int id){
        Optional<StockPrice> stockPrice = stockPriceRespository.findById(id);
        return stockPrice.get();
    }

    public List<StockPrice> getStockPriceBySymbol(String symbol){
        List<StockPrice> stockPriceList = stockPriceRespository.findByStock_Symbol(symbol);
        if(!stockPriceList.isEmpty()){
            return stockPriceList;
        }
        else{
            return null;
        }
    }
    
    @Transactional
    public StockPrice save(StockPrice stockPrice) {
        return stockPriceRespository.save(stockPrice);
    }


    @Transactional
    public void deleteById(int id) {
        stockPriceRespository.deleteById(id);
    }
}
