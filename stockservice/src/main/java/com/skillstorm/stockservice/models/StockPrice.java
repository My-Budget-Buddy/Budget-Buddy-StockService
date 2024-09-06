package com.skillstorm.stockservice.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class StockPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;
    @Column(nullable = false)
    private BigDecimal openPrice;

    @Column(nullable = false)
    private BigDecimal highPrice;

    @Column(nullable = false)
    private BigDecimal lowPrice;

    @Column(nullable = false)
    private BigDecimal currentPrice;

    @Column(nullable = false)
    private Long volume;

    @Column(nullable = false)
    private BigDecimal previousClose;

    @Column(nullable = false)
    private BigDecimal priceChange;

    @Column(nullable = false)
    private BigDecimal changePercent;

    @Column(nullable = false)
    private LocalDate tradingDay;

    public StockPrice() {
    }

    public StockPrice(Stock stock, BigDecimal openPrice, BigDecimal highPrice, BigDecimal lowPrice,
            BigDecimal currentPrice, Long volume, BigDecimal previousClose, BigDecimal priceChange,
            BigDecimal changePercent, LocalDate tradingDay) {
        this.stock = stock;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.currentPrice = currentPrice;
        this.volume = volume;
        this.previousClose = previousClose;
        this.priceChange = priceChange;
        this.changePercent = changePercent;
        this.tradingDay = tradingDay;
    }

    public StockPrice(int id, Stock stock, BigDecimal openPrice, BigDecimal highPrice, BigDecimal lowPrice,
            BigDecimal currentPrice, Long volume, BigDecimal previousClose, BigDecimal priceChange,
            BigDecimal changePercent, LocalDate tradingDay) {
        this.id = id;
        this.stock = stock;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.currentPrice = currentPrice;
        this.volume = volume;
        this.previousClose = previousClose;
        this.priceChange = priceChange;
        this.changePercent = changePercent;
        this.tradingDay = tradingDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public BigDecimal getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(BigDecimal previousClose) {
        this.previousClose = previousClose;
    }

    public BigDecimal getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(BigDecimal priceChange) {
        this.priceChange = priceChange;
    }

    public BigDecimal getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(BigDecimal changePercent) {
        this.changePercent = changePercent;
    }

    public LocalDate getTradingDay() {
        return tradingDay;
    }

    public void setTradingDay(LocalDate tradingDay) {
        this.tradingDay = tradingDay;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((stock == null) ? 0 : stock.hashCode());
        result = prime * result + ((openPrice == null) ? 0 : openPrice.hashCode());
        result = prime * result + ((highPrice == null) ? 0 : highPrice.hashCode());
        result = prime * result + ((lowPrice == null) ? 0 : lowPrice.hashCode());
        result = prime * result + ((currentPrice == null) ? 0 : currentPrice.hashCode());
        result = prime * result + ((volume == null) ? 0 : volume.hashCode());
        result = prime * result + ((previousClose == null) ? 0 : previousClose.hashCode());
        result = prime * result + ((priceChange == null) ? 0 : priceChange.hashCode());
        result = prime * result + ((changePercent == null) ? 0 : changePercent.hashCode());
        result = prime * result + ((tradingDay == null) ? 0 : tradingDay.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StockPrice other = (StockPrice) obj;
        if (id != other.id)
            return false;
        if (stock == null) {
            if (other.stock != null)
                return false;
        } else if (!stock.equals(other.stock))
            return false;
        if (openPrice == null) {
            if (other.openPrice != null)
                return false;
        } else if (!openPrice.equals(other.openPrice))
            return false;
        if (highPrice == null) {
            if (other.highPrice != null)
                return false;
        } else if (!highPrice.equals(other.highPrice))
            return false;
        if (lowPrice == null) {
            if (other.lowPrice != null)
                return false;
        } else if (!lowPrice.equals(other.lowPrice))
            return false;
        if (currentPrice == null) {
            if (other.currentPrice != null)
                return false;
        } else if (!currentPrice.equals(other.currentPrice))
            return false;
        if (volume == null) {
            if (other.volume != null)
                return false;
        } else if (!volume.equals(other.volume))
            return false;
        if (previousClose == null) {
            if (other.previousClose != null)
                return false;
        } else if (!previousClose.equals(other.previousClose))
            return false;
        if (priceChange == null) {
            if (other.priceChange != null)
                return false;
        } else if (!priceChange.equals(other.priceChange))
            return false;
        if (changePercent == null) {
            if (other.changePercent != null)
                return false;
        } else if (!changePercent.equals(other.changePercent))
            return false;
        if (tradingDay == null) {
            if (other.tradingDay != null)
                return false;
        } else if (!tradingDay.equals(other.tradingDay))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "StockPrice [id=" + id + ", stock=" + stock + ", openPrice=" + openPrice + ", highPrice=" + highPrice
                + ", lowPrice=" + lowPrice + ", currentPrice=" + currentPrice + ", volume=" + volume
                + ", previousClose=" + previousClose + ", priceChange=" + priceChange + ", changePercent="
                + changePercent + ", tradingDay=" + tradingDay + ", getClass()=" + getClass() + ", getId()=" + getId()
                + ", getStock()=" + getStock() + ", getOpenPrice()=" + getOpenPrice() + ", getHighPrice()="
                + getHighPrice() + ", getLowPrice()=" + getLowPrice() + ", getCurrentPrice()=" + getCurrentPrice()
                + ", getVolume()=" + getVolume() + ", getPreviousClose()=" + getPreviousClose() + ", getPriceChange()="
                + getPriceChange() + ", getChangePercent()=" + getChangePercent() + ", getTradingDay()="
                + getTradingDay() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }

    
}
