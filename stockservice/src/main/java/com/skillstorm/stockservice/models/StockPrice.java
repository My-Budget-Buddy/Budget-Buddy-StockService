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

}
