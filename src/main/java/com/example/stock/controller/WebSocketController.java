package com.example.stock.controller;

import com.example.stock.dto.StockData;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebSocketController {

    @MessageMapping("/realtime-stock-data")
    @SendTo("/topic/realtime-stock-data")
    public List<StockData> sendRealtimeStockData(List<StockData> stockData) {
        return stockData;
    }

    @MessageMapping("/top-stock-increase")
    @SendTo("/topic/top-stock-increase")
    public List<StockData> sendTopStockIncrease(List<StockData> topStockIncreaseData) {
        return topStockIncreaseData;
    }
}
