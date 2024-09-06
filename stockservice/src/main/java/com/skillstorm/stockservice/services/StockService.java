package com.skillstorm.stockservice.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.skillstorm.stockservice.models.Stock;
import com.skillstorm.stockservice.models.StockDataResponse;
import com.skillstorm.stockservice.models.StockPrice;
import com.skillstorm.stockservice.repositories.StockPriceRespository;
import com.skillstorm.stockservice.repositories.StockRepository;

@Service
public class StockService {

    private static final String API_KEY = "myKey";
    private static final String API_URL = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey="
            + API_KEY;

    @Autowired
    private StockPriceRespository stockPriceRespository;

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Stock getStockById(int id) {
        Optional<Stock> stock = stockRepository.findById(id);
        return stock.orElse(null);
    }

    public Stock updateStock(int id, Stock updatedStock) {
        Optional<Stock> existingStock = stockRepository.findById(id);
        if (existingStock.isPresent()) {
            Stock stock = existingStock.get();
            stock.setSymbol(updatedStock.getSymbol());
            stock.setCompanyName(updatedStock.getCompanyName());
            return stockRepository.save(stock);
        } else {
            return null;
        }
    }

    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public void deleteStock(int id) throws Exception {
        if (!stockRepository.existsById(id)) {
            throw new Exception("Stock with id: " + id + " not found");
        }
        stockRepository.deleteById(id);
    }

    public void updateStockData(String symbol) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(API_URL, symbol);

        StockDataResponse response = restTemplate.getForObject(url, StockDataResponse.class);
        if (response != null && response.getGlobalQuote() != null) {
            Map<String, String> quoteData = response.getGlobalQuote();

            Stock stock = stockRepository.findBySymbol(symbol);

            if (stock == null) {
                stock = new Stock();
                stock.setSymbol(symbol);
                stockRepository.save(stock);
            }

            StockPrice stockPrice = new StockPrice();
            stockPrice.setStock(stock);
            stockPrice.setOpenPrice(new BigDecimal(quoteData.get("02. open")));
            stockPrice.setHighPrice(new BigDecimal(quoteData.get("03. high")));
            stockPrice.setLowPrice(new BigDecimal(quoteData.get("04. low")));
            stockPrice.setCurrentPrice(new BigDecimal(quoteData.get("05. price")));
            stockPrice.setVolume(Long.parseLong(quoteData.get("06. volume")));
            stockPrice.setPreviousClose(new BigDecimal(quoteData.get("08. previous close")));
            stockPrice.setPriceChange(new BigDecimal(quoteData.get("09. change")));
            stockPrice.setChangePercent(new BigDecimal(quoteData.get("10. change percent").replace("%", "")));
            stockPrice.setTradingDay(LocalDate.parse(quoteData.get("07. latest trading day")));

            stockPriceRespository.save(stockPrice);
        } else {
            throw new Exception("Error fetching stock data from API for symbol: " + symbol);
        }
    }

}
