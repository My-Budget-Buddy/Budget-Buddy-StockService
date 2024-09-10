package com.skillstorm.stockservice.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Inventory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @ManyToOne
    @JoinColumn(name = "stock_price_id", nullable = false)
    private StockPrice stockPrice;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal purchasePrice;

    @Column(nullable = false)
    private LocalDateTime purchaseDate;

    public Inventory() {
    }

    public Inventory(int userId, StockPrice stockPrice, Integer quantity, BigDecimal purchasePrice,
            LocalDateTime purchaseDate) {
        this.userId = userId;
        this.stockPrice = stockPrice;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.purchaseDate = purchaseDate;
    }

    public Inventory(int id, int userId, StockPrice stockPrice, Integer quantity, BigDecimal purchasePrice,
            LocalDateTime purchaseDate) {
        this.id = id;
        this.userId = userId;
        this.stockPrice = stockPrice;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.purchaseDate = purchaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public StockPrice getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(StockPrice stockPrice) {
        this.stockPrice = stockPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + userId;
        result = prime * result + ((stockPrice == null) ? 0 : stockPrice.hashCode());
        result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
        result = prime * result + ((purchasePrice == null) ? 0 : purchasePrice.hashCode());
        result = prime * result + ((purchaseDate == null) ? 0 : purchaseDate.hashCode());
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
        Inventory other = (Inventory) obj;
        if (id != other.id)
            return false;
        if (userId != other.userId)
            return false;
        if (stockPrice == null) {
            if (other.stockPrice != null)
                return false;
        } else if (!stockPrice.equals(other.stockPrice))
            return false;
        if (quantity == null) {
            if (other.quantity != null)
                return false;
        } else if (!quantity.equals(other.quantity))
            return false;
        if (purchasePrice == null) {
            if (other.purchasePrice != null)
                return false;
        } else if (!purchasePrice.equals(other.purchasePrice))
            return false;
        if (purchaseDate == null) {
            if (other.purchaseDate != null)
                return false;
        } else if (!purchaseDate.equals(other.purchaseDate))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Inventory [id=" + id + ", userId=" + userId + ", stockPrice=" + stockPrice + ", quantity=" + quantity
                + ", purchasePrice=" + purchasePrice + ", purchaseDate=" + purchaseDate + ", getClass()=" + getClass()
                + ", getId()=" + getId() + ", getUserId()=" + getUserId() + ", getStockPrice()=" + getStockPrice()
                + ", getQuantity()=" + getQuantity() + ", getPurchasePrice()=" + getPurchasePrice()
                + ", getPurchaseDate()=" + getPurchaseDate() + ", hashCode()=" + hashCode() + ", toString()="
                + super.toString() + "]";
    }

   

    
}
