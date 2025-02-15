package com.pratik.stockscreener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StockscreenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockscreenerApplication.class, args);
	}

}
