package com.pratik.stockscreener.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratik.stockscreener.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    @Value("${alpha.vantage.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Cacheable(value = "stockData", key = "#symbol")
    public JsonNode getStockData(String symbol) {
        String url = String.format("https://www.alphavantage.co/query?function=OVERVIEW&symbol=%s&apikey=%s", symbol, apiKey);
        String response = restTemplate.getForObject(url, String.class);

        try {
            return objectMapper.readTree(response);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse stock data", e);
        }
    }

    public String getStockPrice(String symbol) {
        JsonNode stockData = getStockData(symbol);
        String latestDate = stockData.at("/Meta Data/3. Last Refreshed").asText();
        String closePrice = stockData.at("/Time Series (Daily)/" + latestDate + "/4. close").asText();
        return "Latest close price for " + symbol + " on " + latestDate + ": " + closePrice;
    }

    @Cacheable(value = "allStocks")
    public List<Stock> getAllStocks() {
        String url = String.format("https://www.alphavantage.co/query?function=LISTING_STATUS&apikey=%s", apiKey);
        String response = restTemplate.getForObject(url, String.class);

        try {
            // Parse the CSV response
            List<Stock> stocks = new ArrayList<>();
            String[] lines = response.split("\n");
            for (int i = 1; i < lines.length; i++) { // Skip header
                String[] data = lines[i].split(",");
                String symbol = data[0];
                String name = data[1];
                String exchange = data[2];
                stocks.add(new Stock(symbol, name, exchange));
            }
            return stocks;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse stock list", e);
        }
    }
}
