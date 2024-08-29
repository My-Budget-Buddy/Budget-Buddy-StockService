package com.skillstorm.stockservice.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String symbol;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    
    public Stock() {
    }


    public Stock(String symbol, String companyName, LocalDateTime createdAt) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.createdAt = createdAt;
    }


    public Stock(int id, String symbol, String companyName, LocalDateTime createdAt) {
        this.id = id;
        this.symbol = symbol;
        this.companyName = companyName;
        this.createdAt = createdAt;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getSymbol() {
        return symbol;
    }


    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


    public String getCompanyName() {
        return companyName;
    }


    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
        result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
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
        Stock other = (Stock) obj;
        if (id != other.id)
            return false;
        if (symbol == null) {
            if (other.symbol != null)
                return false;
        } else if (!symbol.equals(other.symbol))
            return false;
        if (companyName == null) {
            if (other.companyName != null)
                return false;
        } else if (!companyName.equals(other.companyName))
            return false;
        if (createdAt == null) {
            if (other.createdAt != null)
                return false;
        } else if (!createdAt.equals(other.createdAt))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Stock [id=" + id + ", symbol=" + symbol + ", companyName=" + companyName + ", createdAt=" + createdAt
                + ", getId()=" + getId() + ", getSymbol()=" + getSymbol() + ", getCompanyName()=" + getCompanyName()
                + ", getClass()=" + getClass() + ", getCreatedAt()=" + getCreatedAt() + ", hashCode()=" + hashCode()
                + ", toString()=" + super.toString() + "]";
    }

    
   

    
}
