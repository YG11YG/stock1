package com.example.stock.service;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.Map;

public class ExchangeRateService {}

   /** private static final String EXCHANGE_URL = "https://m.search.naver.com/p/csearch/content/qapirender.nhn?key=calculator&pkid=141&q=환율&where=m&u1=keb&u6=standardUnit&u7=0&u3=USD&u4=KRW&u8=down&u2=1";

    public double getExchangeRate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.getForEntity(EXCHANGE_URL, Map.class);
        Map<String, Object> responseBody = response.getBody();
        // 여기서는 JSON 구조에 따라 적절히 파싱해야 합니다.
        // 예를 들어, "value": "1,330.50" 부분을 추출하고 변환합니다.
        String exchangeRateString = (String) ((Map<String, Object>) responseBody.get("country")).get("value");
        return Double.parseDouble(exchangeRateString.replace(",", ""));
    }
    public double convertKRWtoUSD(double amountInKRW) {
        double exchangeRate = getExchangeRate();
        return amountInKRW / exchangeRate;
    }
} **/