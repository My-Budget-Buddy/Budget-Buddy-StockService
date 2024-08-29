package com.skillstorm.stockservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.stockservice.models.Stock;
import com.skillstorm.stockservice.repositories.StockRepository;

@Service
public class StockService {
    
    @Autowired
    private StockRepository stockRepository;

    public List<Stock> getAllStocks(){
        return stockRepository.findAll();
    }

    public Stock getStockById(int id){
        Optional<Stock> stock = stockRepository.findById(id);
        return stock.orElse(null);
    }

    public Stock updateStock(int id, Stock updatedStock){
        Optional<Stock> existingStock = stockRepository.findById(id);
        if(existingStock.isPresent()){
            Stock stock = existingStock.get();
            stock.setSymbol(updatedStock.getSymbol());
            stock.setCompanyName(updatedStock.getCompanyName());
            return stockRepository.save(stock);
        } else{
            return null;
        }
    }

    public Stock createStock(Stock stock){
        return stockRepository.save(stock);
    }

    public void deleteStock(int id){
        if(!stockRepository.existsById(id)){

        }
    }
}
