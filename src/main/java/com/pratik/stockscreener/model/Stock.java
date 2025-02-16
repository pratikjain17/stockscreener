package com.pratik.stockscreener.model;

import lombok.Data;

@Data
public class Stock {
    private String symbol;
    private String name;
    private double price;
    private double marketCap;
    private double debtToEquity;
    private double roce;      // Return on Capital Employed
    private double roe;       // Return on Equity
    private double peRatio;
}
