package com.pratik.stockscreener.controller;

import com.pratik.stockscreener.model.Stock;
import com.pratik.stockscreener.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/{symbol}")
    public ResponseEntity<String> getStockData(@PathVariable String symbol) {
        try {
            String data = String.valueOf(stockService.getStockData(symbol));
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching stock data: " + e.getMessage());
        }
    }

    @GetMapping("/{symbol}/price")
    public ResponseEntity<String> getStockPrice(@PathVariable String symbol) {
        try {
            String price = stockService.getStockPrice(symbol);
            return ResponseEntity.ok(price);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching stock data: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllStocks() {
        try {
            List<Stock> topStocks = stockService.getAllStocks();
            return ResponseEntity.ok(topStocks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching stock data: " + e.getMessage());
        }
    }

    @GetMapping("/screen")
    public ResponseEntity<List<Stock>> screenStocks(
            @RequestParam(required = false) Double maxDebtToEquity,
            @RequestParam(required = false) Double minROCE,
            @RequestParam(required = false) Double minROE,
            @RequestParam(required = false) Double minMarketCap,
            @RequestParam(required = false) Double maxPERatio
    ) {
        List<Stock> stockList = stockService.screenStocks(
                maxDebtToEquity,
                minROCE,
                minROE,
                minMarketCap,
                maxPERatio
        );

        return ResponseEntity.ok(stockList);
    }
}
