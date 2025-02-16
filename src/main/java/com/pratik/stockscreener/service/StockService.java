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
        return stockData.at("/Time Series (Daily)/" + latestDate + "/4. close").asText();
    }

    @Cacheable(value = "allStocks")
    public List<Stock> getAllStocks() {
        String url = String.format("https://www.alphavantage.co/query?function=LISTING_STATUS&apikey=%s", apiKey);
        String response = restTemplate.getForObject(url, String.class);

        try {
            // Parse the CSV response
            List<Stock> stocks = new ArrayList<>();
            String[] lines = response.split("\n");
            for (int i = 1; i < lines.length; i++) {
                String[] data = lines[i].split(",");
                String symbol = data[0];
                JsonNode stockDetails = getStockData(symbol);
                stocks.add(mapJsonToStock(stockDetails));
            }
            return stocks;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse stock list", e);
        }
    }

    private Stock mapJsonToStock(JsonNode stockDetails) {
        Stock stock = new Stock();
        stock.setSymbol(stockDetails.get("Symbol").toString());
        stock.setName(stockDetails.get("Name").toString());
        stock.setPrice(stockDetails.get("52WeekHigh").asDouble()); //for now taking 52week high price because of not enough data
        stock.setMarketCap(stockDetails.get("MarketCapitalization").asDouble());
        stock.setDebtToEquity(stockDetails.get("PriceToBookRatio").asDouble()); //for now taking pricetobook value because not enough data
        stock.setRoce(stockDetails.get("ReturnOnAssetsTTM").asDouble());
        stock.setRoe(stockDetails.get("ReturnOnEquityTTM").asDouble());
        stock.setPeRatio(stockDetails.get("PERatio").asDouble());
        return stock;
    }

    public List<Stock> screenStocks(Double maxDebtToEquity, Double minROCE, Double minROE, Double minMarketCap, Double maxPERatio) {
        List<Stock> allStocks = getAllStocks();

        return allStocks.stream()
                .filter(stock -> maxDebtToEquity == null || stock.getDebtToEquity() <= maxDebtToEquity)
                .filter(stock -> minROCE == null || stock.getRoce() >= minROCE)
                .filter(stock -> minROE == null || stock.getRoe() >= minROE)
                .filter(stock -> minMarketCap == null || stock.getMarketCap() >= minMarketCap)
                .filter(stock -> maxPERatio == null || stock.getPeRatio() <= maxPERatio)
                .toList();
    }
}
