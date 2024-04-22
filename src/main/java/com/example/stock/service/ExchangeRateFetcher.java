package com.example.stock.service;

import java.io.BufferedReader; // 버퍼를 사용해 입력 스트림으로부터 텍스트를 읽기 위한 클래스를 임포트합니다.
import java.io.InputStreamReader; // 바이트 스트림을 문자 스트림으로 변환하기 위한 클래스를 임포트합니다.
import java.net.HttpURLConnection; // HTTP 요청을 보내기 위한 클래스를 임포트합니다.
import java.net.URL; // URL을 다루기 위한 클래스를 임포트합니다.

import org.json.JSONArray;
import org.json.JSONObject; // JSON 객체를 다루기 위한 클래스를 임포트합니다.
import org.springframework.stereotype.Service;

@Service
public class ExchangeRateFetcher {

    // 환율을 가져오는 메서드
    public static double getExchangeRate() throws Exception {
        String url = "https://m.search.naver.com/p/csearch/content/qapirender.nhn?key=calculator&pkid=141&q=환율&where=m&u1=keb&u6=standardUnit&u7=0&u3=USD&u4=KRW&u8=down&u2=1";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();


        JSONObject myResponse = new JSONObject(response.toString());

        JSONArray countryArray = myResponse.getJSONArray("country");
        JSONObject firstCountry = countryArray.getJSONObject(1);
        String value = firstCountry.getString("value");

        return Double.parseDouble(value.replace(",", ""));
    }

    public static double calculateUSDPerKRW(double value) {

        return 1 / value;
    }
}
