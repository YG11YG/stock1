package com.example.stock.openapi;

import com.example.stock.dto.AccessTokenResponse;
import com.example.stock.entity.AccessEntity;
import com.example.stock.repository.AccessRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
@Service

public class TokenRequest {

    @Autowired
    private AccessRepository accessRepository;

    public String getAccessToken() throws IOException {
        // API Endpoint
        String url = "https://openapi.koreainvestment.com:9443/oauth2/tokenP";
//접근 토큰 받는 클라스
        // 요청 본문
        String json = "{\"grant_type\": \"client_credentials\", " +
                "\"appkey\": \"PSv7KA272Ztvq5laNkJhvtCszZv1gMJyLHLz\", " +
                "\"appsecret\": \"h9YuY7ehQFHvR3cuow9wyDvTuJpemccx4lndHY+rvzonoq8T/QyvJ7kJ3aMQP7Q6/iqojTZmncUcp2W3EWR8sNfL54tCWfB5Zd4okPqNkAdsmo6iDO91l5UUo4Wj96mNcLuK5EH7QFWRPFOm5k/IlcI3OwmpxUFscV+3sy153WnprBlxGVc=\"}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json; charset=UTF-8")
                .POST(BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        //    System.out.println("Response status code: " + response.statusCode());
        //    System.out.println("Response body: " + response.body());


            // JSON 응답을 Java 객체로 변환
            ObjectMapper mapper = new ObjectMapper();
            AccessTokenResponse accessTokenResponse = mapper.readValue(response.body(), AccessTokenResponse.class);
// Access Token을 AccessEntity 객체로 변환
           // AccessEntity accessEntity = accessTokenResponse.toEntity();

            // 변환된 AccessEntity 객체를 데이터베이스에 저장
           // accessRepository.save(accessEntity);
            // 필요한 access_token 값 출력
            System.out.println("Access Token: " + accessTokenResponse.getAccess_token());
            return "Bearer " + accessTokenResponse.getAccessToken();
            // accessRepository에서 가장 최근에 저장된 AccessEntity 가져오기
            /**
             AccessEntity accessCode = accessRepository.findTopByOrderByIdDesc();

            // AccessEntity가 존재하면, 그 안의 토큰 반환
            if (accessCode != null) {
                return "Bearer " + accessCode;
            } else {
                return null; // 또는 적절한 예외 처리
            }
**/
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
