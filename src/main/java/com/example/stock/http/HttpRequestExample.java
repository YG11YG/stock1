package com.example.stock.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject; // JSON 처리를 위한 라이브러리

public class HttpRequestExample {

    public static void main(String[] args) {
        try {
            String url = "https://example.com/api"; // API 엔드포인트 URL
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // HTTP 요청 메소드 설정
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            // 요청 본문에 JSON 데이터 설정
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("key1", "value1");
            jsonInput.put("key2", "value2");

            // 요청 전송
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInput.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 응답 받기
            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // 응답 데이터 출력
            System.out.println(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
