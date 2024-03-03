package com.example.stock.data;

import java.util.Arrays;
import java.util.List;

public class StockCodeDate {
    private List<String> stockCodes;

    public StockCodeDate() {
        this.stockCodes = Arrays.asList("460860", "462020", "462520", "464440", "464680", "465320", "465770", "466910");
    }


    public List<String> getStockCodes() {
        return stockCodes;
    }
}
