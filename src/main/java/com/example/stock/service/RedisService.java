package com.example.stock.service;

import com.example.stock.dto.StockData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveRealtimeStockData(List<StockData> stockData) {
        for (StockData stock : stockData) {
            redisTemplate.opsForValue().set(String.valueOf(stock.getId()), stock.getFirst_name());
        }
    }

    public List<Object> getRealtimeStockData() {
        List<String> keys = redisTemplate.keys("*").stream().collect(Collectors.toList());
        return redisTemplate.opsForValue().multiGet(keys);
    }
}

