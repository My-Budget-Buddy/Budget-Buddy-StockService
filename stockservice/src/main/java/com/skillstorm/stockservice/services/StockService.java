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

    // get a list of all stocks
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    // get stock by id
    public Stock getStockById(int id) {
        Optional<Stock> stock = stockRepository.findById(id);
        return stock.orElse(null);
    }

    // get stock by its symbol
    public Stock getStockBySymbol(String symbol){
        Stock stock = stockRepository.findBySymbol(symbol);
        if(stock != null){
            return stock;
        }
        else{
            return null;
        }
    }

    // update stock 
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

    // create stock
    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }

    // delete stock by id
    public void deleteStock(int id) throws Exception {
        if (!stockRepository.existsById(id)) {
            throw new Exception("Stock with id: " + id + " not found");
        }
        stockRepository.deleteById(id);
    }

    // third party API call to update stocks when searched for on frontend
    public void updateStockData(String symbol) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        // setup the url with symbol to call third party API
        String url = String.format(API_URL, symbol);

        // call the third-party API and map the response to StockDataResponse class
        StockDataResponse response = restTemplate.getForObject(url, StockDataResponse.class);

        // check if the response is valid and contains the "Global Quote" data
        if (response != null && response.getGlobalQuote() != null) {

            // extract the data
            Map<String, String> quoteData = response.getGlobalQuote();

            // fetch the Stock entity from the database using the symbol
            Stock stock = stockRepository.findBySymbol(symbol);

            // if the stock doesn't exist in the database, create a new Stock entity
            if (stock == null) {
                stock = new Stock();
                stock.setSymbol(symbol);
                stockRepository.save(stock);
            }

            // create a new StockPrice entity and populate it with the data from the API response
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

            // save the StockPrice entity in the repository
            stockPriceRespository.save(stockPrice);
        } else {
            throw new Exception("Error fetching stock data from API for symbol: " + symbol);
        }
    }

}
