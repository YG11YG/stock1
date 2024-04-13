package com.example.stock.openapi;

import com.example.stock.dto.MainDataDto;
import com.example.stock.entity.MainDataEntity;
import com.example.stock.repository.DataRepository;
import com.example.stock.repository.MainDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class MainDataApi {

    @Autowired
    private MainDataRepository mainDataRepository;
    @Autowired
    private TokenRequest tokenRequest;
    @Autowired
    private DataRepository dataRepository;
    private static final int INITIAL_BACKOFF_INTERVAL = 1000; // 초기 대기 시간 (1초)
    private static final int MAX_BACKOFF_INTERVAL = 32000; // 최대 대기 시간 (32초)
    private static final int MAX_RETRY_ATTEMPTS = 5; // 최대 재시도 횟수

    @Bean
    public void fetchStockData() throws IOException {

        String authorization = tokenRequest.getAccessToken();//"bear" + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b2tlbiIsImF1ZCI6ImJkZjU4MzhiLTMwMTktNDQ5YS1hYzg4LWQ2ZjdhMjM3NGRkYyIsImlzcyI6InVub2d3IiwiZXhwIjoxNzA1NTY4NTM4LCJpYXQiOjE3MDU0ODIxMzgsImp0aSI6IlBTdjdLQTI3Mlp0dnE1bGFOa0podnRDc3padjFnTUp5TEhMeiJ9.On3tlcuHAoz8aHtNF-6V_XTX75S18pNPTlL5ttoMfwPvctbNM8OZUU1gb-2-PyGgHVNp7lY71BAM32FKspJO_A";//

        Pageable limit = PageRequest.of(0, 100);
        List<String> stockCodes = dataRepository.findAllStockCodes(limit);//이거 들어갈때 리포지터리에 쿼리를 작성해야한다


        for (String fid_input_iscd : stockCodes) {

// 백오프 알고리즘을 위한 변수
            int currentBackoffInterval = INITIAL_BACKOFF_INTERVAL;
            int retryCount = 0;

            while (retryCount < MAX_RETRY_ATTEMPTS) {
                try {
                    URL url = new URL("https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-price?tr_id=" + "FHKST01010100" + "&fid_cond_mrkt_div_code=" + "J" + "&fid_input_iscd=" + fid_input_iscd);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                   // connection.setConnectTimeout(10000); // 연결 타임아웃 10초
                   // connection.setReadTimeout(20000);    // 읽기 타임아웃 20초

                    //connection.setRequestMethod("GET");

                    connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                    connection.setRequestProperty("authorization", authorization);
                    connection.setRequestProperty("appKey", "PSv7KA272Ztvq5laNkJhvtCszZv1gMJyLHLz");
                    connection.setRequestProperty("appSecret", "h9YuY7ehQFHvR3cuow9wyDvTuJpemccx4lndHY+rvzonoq8T/QyvJ7kJ3aMQP7Q6/iqojTZmncUcp2W3EWR8sNfL54tCWfB5Zd4okPqNkAdsmo6iDO91l5UUo4Wj96mNcLuK5EH7QFWRPFOm5k/IlcI3OwmpxUFscV+3sy153WnprBlxGVc=");
                    connection.setRequestProperty("tr_id", "FHKST01010100");
                    connection.connect();
                    try (InputStream inputStream = connection.getInputStream();
                         InputStreamReader isr = new InputStreamReader(inputStream);
                         BufferedReader br = new BufferedReader(isr)) {
                        String line;
                        StringBuilder response = new StringBuilder();
                        while ((line = br.readLine()) != null) {
                            response.append(line);
                        }

                        ObjectMapper mapper = new ObjectMapper();



                        MainDataDto mainData = mapper.readValue(response.toString(), MainDataDto.class);
                       //System.out.println(mainData.toString());

                        mainData.setStockCodes(fid_input_iscd); // 적절한 메소드로 stockCode 조회
                        MainDataEntity mainDataEntity = mainData.toEntity(); // DTO를 Entity로 변환


                        mainDataRepository.save(mainDataEntity); // 변환된 Entity를 저장


                }

                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                        // 재시도 카운트 증가
                        retryCount++;
                        try {
                            // 재시도 전 대기
                            Thread.sleep(currentBackoffInterval);
                        } catch (InterruptedException j) {
                            Thread.currentThread().interrupt(); // 인터럽트 상태 복원
                            throw new RuntimeException(j); // 또는 다른 적절한 예외 처리
                        }
                        // 대기 시간 증가 (2배)
                        currentBackoffInterval = Math.min(currentBackoffInterval * 2, MAX_BACKOFF_INTERVAL);
                    }
                }
            }
        }
    }

