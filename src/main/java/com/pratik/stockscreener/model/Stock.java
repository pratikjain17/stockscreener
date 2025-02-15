package com.pratik.stockscreener.model;

import lombok.Data;

@Data
public class Stock {
    private String symbol;
    private String name;
    private String exchange;

    public Stock(String symbol, String name, String exchange) {
        this.symbol = symbol;
        this.name = name;
        this.exchange = exchange;
    }

    // Getters and Setters
}
