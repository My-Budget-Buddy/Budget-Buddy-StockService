package com.skillstorm.stockservice.models;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockDataResponse {
    
    @JsonProperty("Global Quote")
    private Map<String, String> globalQuote;

    public Map<String, String> getGlobalQuote(){
        return globalQuote;
    }

    public void setGlobalQuote(Map<String, String> globalQuote){
        this.globalQuote = globalQuote;
    }
}
